package bank.client.driver.rest;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

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
        return r.request().accept(MediaType.TEXT_HTML).post(Entity.form(f)).toString();
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        return false;
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        return null;
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
