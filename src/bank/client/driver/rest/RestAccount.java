package bank.client.driver.rest;

import java.io.IOException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.server.AccountImpl;

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
        Response res = r.path("/"+ number).request().head();
        if(res.getStatus() == Status.NOT_FOUND.getStatusCode()){
            throw new NotFoundException();
        }else if(res.getStatus() == Status.GONE.getStatusCode())
            return false;
        else if(res.getStatus() == 200)
            return true;
        else
            throw new BadRequestException();
    }

    @Override
    public void deposit(double amount) throws IOException,
            IllegalArgumentException, InactiveException {
        if(amount < 0.0d)
            throw new IllegalArgumentException();
        
        double newBalance = getBalance();
        newBalance += amount;
        try {
            updateBalance(newBalance);
        } catch (OverdrawException e) {
            throw new IllegalStateException("Can't deposit negative amount");
        }
    }

    @Override
    public void withdraw(double amount) throws IOException,
            IllegalArgumentException, OverdrawException, InactiveException {
        if(amount < 0.0d)
            throw new IllegalArgumentException();
        
        double newBalance = getBalance();
        newBalance -= amount;
        updateBalance(newBalance);
    }
    
    //Idempotente Hilfsmethode
    private void updateBalance(double newBalance) throws InactiveException, OverdrawException{
        Form f = new Form();
        f.param("newBalance", ""+newBalance);
        Response res = r.path("/"+ number).request().put(Entity.form(f));
        if(res.getStatus() == Status.NOT_FOUND.getStatusCode())
            throw new IllegalArgumentException();
        else if(res.getStatus() == Status.GONE.getStatusCode())
            throw new InactiveException();
        else if(res.getStatus() == 418)
            throw new OverdrawException();
    }

    @Override
    public double getBalance() throws IOException {
        AccountImpl acc = r.path("/"+ number).request().accept(MediaType.APPLICATION_JSON).get(AccountImpl.class);
        return acc.getBalance();
    }
    
}
