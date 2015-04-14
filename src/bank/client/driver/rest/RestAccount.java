package bank.client.driver.rest;

import java.io.IOException;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public class RestAccount implements Account {

    @Override
    public String getNumber() throws IOException {
        return null;
    }

    @Override
    public String getOwner() throws IOException {
        return null;
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
