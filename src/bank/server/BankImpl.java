package bank.server;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public class BankImpl implements bank.Bank {

	private final Map<String, AccountImpl> accounts = new ConcurrentHashMap<>(); //Concurrent: Thread-safe

	private volatile int nextNumber = 0; //Volatile: Thread-safe
	
	@Override
	public Set<String> getAccountNumbers() throws IOException {
        return accounts.values().stream()
                .filter(acc -> acc.isActive())
                .map(acc -> acc.getNumber())
                .collect(Collectors.toSet());
	}

	@Override
	public synchronized String createAccount(String owner) throws IOException {
		String number = "CH"+nextNumber++;
		AccountImpl acc = new AccountImpl(owner, number);
		accounts.put(number, acc);
		return number;
	}

	@Override
	public boolean closeAccount(String number) throws IOException {
	    AccountImpl acc = accounts.get(number);
		if(acc != null){
			return acc.close();
		} else {		    
		    return false; //Account nicht vorhanden
		}
	}

	@Override
	public Account getAccount(String number) throws IOException {
		return accounts.get(number);
	}

	@Override
	public synchronized void transfer(bank.Account from, bank.Account to, double amount) throws IOException, InactiveException,
	        IllegalArgumentException, OverdrawException {
		from.withdraw(amount);
		to.deposit(amount); // XXX falsch, denn to könnte inaktiv sein, dann wird zwar eine InactiveException geworfen, aber auf dem Konto from ist der Betrag bereits belastet worden.
	}

}
