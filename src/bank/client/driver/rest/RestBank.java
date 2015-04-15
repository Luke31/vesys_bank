package bank.client.driver.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

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
        Response res = r.request().post(Entity.form(f));//.toString();
        if(res.getStatus() == Status.CREATED.getStatusCode()){
            String num = res.readEntity(String.class);
            return num;
        }else
            throw new InternalServerErrorException(res);
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        return false;
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        String[] accs = r.request().accept(MediaType.APPLICATION_JSON).get(String[].class);
        return new HashSet<String>(Arrays.asList(accs));
    }

    @Override
    public Account getAccount(String number) throws IOException {
        
        return null;
    }

    @Override
    public void transfer(Account a, Account b, double amount)
            throws IOException, IllegalArgumentException, OverdrawException,
            InactiveException {
        
    }

}
