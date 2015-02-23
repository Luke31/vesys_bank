package bank;

import java.io.IOException;
import java.io.Serializable;

public class AccountImpl implements bank.Account, Serializable{

    private static final long serialVersionUID = 4786217009595468329L;
    
    private String number;
	private String owner;
	private double balance;
	private boolean active = true;

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
	public synchronized void deposit(double amount) throws InactiveException {
		amount = Math.abs(amount);
		if(!isActive())
			throw new InactiveException();
		else
			balance += amount;
	}

	@Override
	public synchronized void withdraw(double amount) throws InactiveException, OverdrawException {
		amount = Math.abs(amount);
		if(!isActive())
			throw new InactiveException();
		else if(balance - amount < 0)
			throw new OverdrawException();
		else
			balance -= amount;				
	}

	@Override
	public synchronized boolean close() throws IOException {
		if(isActive() && balance == 0){
			active = false;
			return true;
		}else
			return false;
	}

	
}
