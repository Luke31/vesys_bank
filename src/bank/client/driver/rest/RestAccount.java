package bank.client.driver.rest;

import java.io.IOException;

import javax.ws.rs.client.WebTarget;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public class RestAccount implements Account {

    private String number;
    private String owner;
    private WebTarget r;
    
    public RestAccount(String number, String owner, WebTarget r) {
        super();
        this.number = number;
        this.owner = owner;
        this.r = r;
    }

    @Override
    public String getNumber() throws IOException {
        return number;
    }

    @Override
    public String getOwner() throws IOException {
        return owner;
    }

    @Override
    public boolean isActive() throws IOException {
        return false;
    }

    @Override
    public void deposit(double amount) throws IOException,
            IllegalArgumentException, InactiveException {
        
    }

    @Override
    public void withdraw(double amount) throws IOException,
            IllegalArgumentException, OverdrawException, InactiveException {
        
    }

    @Override
    public double getBalance() throws IOException {
        return 0;
    }
    
}
