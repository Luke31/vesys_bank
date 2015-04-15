package bank.server;

import java.io.IOException;

import bank.InactiveException;
import bank.OverdrawException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class AccountImpl implements bank.Account{
    
    private String number;
	private String owner;
	private double balance = 0;
	private boolean active = true;

	public AccountImpl(){
	    
	}
	
	public AccountImpl(String owner, String number) {
		this.owner = owner;
		this.number = number;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public String getOwner() {
		return owner;
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public synchronized void deposit(double amount) throws IllegalArgumentException, InactiveException {
		if(!isActive())
			throw new InactiveException();
		else if(amount < 0.0d)
		    throw new IllegalArgumentException();
		else
		    balance += amount;
	}

	@Override
	public synchronized void withdraw(double amount) throws IllegalArgumentException, OverdrawException, InactiveException {
		if(!isActive())
			throw new InactiveException();
		else if(amount < 0.0d)
            throw new IllegalArgumentException();
		else if(balance - amount < 0)
			throw new OverdrawException();
		else
			balance -= amount;				
	}

	public synchronized boolean close() throws IOException {
		if(isActive() && balance == 0){
			active = false;
			return true;
		}else
			return false;
	}

	
}
