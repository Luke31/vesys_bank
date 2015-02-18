/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.local;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import bank.InactiveException;
import bank.OverdrawException;

public class Driver implements bank.BankDriver {
	private Bank bank = null;

	@Override
	public void connect(String[] args) {
		bank = new Bank();
		System.out.println("connected...");
	}

	@Override
	public void disconnect() {
		bank = null;
		System.out.println("disconnected...");
	}

	@Override
	public Bank getBank() {
		return bank;
	}

	static class Bank implements bank.Bank {

		private final Map<String, Account> accounts = new ConcurrentHashMap<>(); //Concurrent: Thread-safe

		private volatile int nextNumber = 0; //Volatile: Thread-safe
		
		@Override
		public Set<String> getAccountNumbers() {
			return accounts.values().stream()
				.filter(acc -> acc.isActive())
				.map(acc -> acc.getNumber())
				.collect(Collectors.toSet());
		}

		@Override
		public synchronized String createAccount(String owner) {
			String number = "CH"+nextNumber;
			Account acc = new Account(owner, number);
			accounts.put(number, acc);

			nextNumber++;
			return number;
		}

		@Override
		public boolean closeAccount(String number) {
			Account acc = accounts.get(number);
			if(acc != null){
				try {
					return acc.close();
				} catch (IOException e) {
					return false;
				}
			}else
				return false; //Account nicht vorhanden
		}

		@Override
		public bank.Account getAccount(String number) {
			return accounts.get(number);
		}

		@Override
		public synchronized void transfer(bank.Account from, bank.Account to, double amount) throws IOException, InactiveException,
				OverdrawException {
			from.withdraw(amount);
			to.deposit(amount);
		}

	}

	static class Account implements bank.Account {
		private String number;
		private String owner;
		private double balance;
		private boolean active = true;

		Account(String owner, String number) {
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

}