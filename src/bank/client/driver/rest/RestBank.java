package bank.client.driver.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.server.AccountImpl;

public class RestBank implements Bank {
    private WebTarget r;
    
    public RestBank(WebTarget r) {
        super();
        this.r = r;
    }

    @Override
    public String createAccount(String owner) throws IOException {
        Form f = new Form();
        f.param("owner", owner);
        Response res = r.request().post(Entity.form(f));
        if(res.getStatus() == Status.CREATED.getStatusCode()){
            String num = res.readEntity(String.class);
            return num;
        }else
            throw new InternalServerErrorException(res);
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        if(number == null || number.isEmpty())
            throw new IllegalArgumentException();
        Response res = r.path("/"+ number).request().accept(MediaType.APPLICATION_JSON).delete();
        if(res.getStatus() == 200)
            return true;
        else if(res.getStatus() == Status.NOT_MODIFIED.getStatusCode()) //Already inactive
            return false;
        else
            return false;
    }

    private String[] accs;
    private EntityTag etag = null;
    @Override
    public Set<String> getAccountNumbers() throws IOException {
        Response res = r.request().header("If-None-Match", etag).accept(MediaType.APPLICATION_JSON).get(); 
        if(res.getStatus() != Status.NOT_MODIFIED.getStatusCode()){
            accs = res.readEntity(String[].class);
            etag = res.getEntityTag();
        }
        return new HashSet<String>(Arrays.asList(accs));
    }

    @Override
    public Account getAccount(String number) throws IOException {
        if(number == null || number.isEmpty())
            return null;
        AccountImpl acc = r.path("/"+ number).request().accept(MediaType.APPLICATION_JSON).get(AccountImpl.class);
        if(acc != null)
            return new RestAccount(acc.getNumber(), acc.getOwner(), r);
        return null;
    }

    @Override
    public void transfer(Account a, Account b, double amount)
            throws IOException, IllegalArgumentException, OverdrawException,
            InactiveException {
        RestAccount ar = new RestAccount(a.getNumber(), a.getOwner(), r);
        RestAccount br = new RestAccount(b.getNumber(), b.getOwner(), r);
        ar.withdraw(amount);
        br.deposit(amount);
    }
}