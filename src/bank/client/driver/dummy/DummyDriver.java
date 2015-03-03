/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.client.driver.dummy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import bank.Account;
import bank.Bank;
import bank.client.driver.ClientBankDriver;

/*
 * This class implements a dummy driver which can be used to start and test
 * the GUI application. With this implementation no new accounts can be created
 * nor can accounts be removed. The implementation provides one account which
 * supports the deposit and withdraw operations.
 *
 * @see BankDriver
 */

public class DummyDriver implements ClientBankDriver {
	private Bank bank = null;

	@Override
	public void connect(String[] args) {
		bank = new DummyBank();
		System.out.println("dummy connected...");
	}

	@Override
	public void disconnect() {
		bank = null;
		System.out.println("dummy disconnected...");
	}

	@Override
	public bank.Bank getBank() {
		return bank;
	}

	/** This Dummy-Bank only contains one account. */
	static class DummyBank implements Bank {
		private final Map<String, Account> accounts = new HashMap<String, Account>();
		{
			DummyAccount acc = new DummyAccount();
			accounts.put(acc.getNumber(), acc);
		}

		@Override
		public String createAccount(String owner) {
			return null; // returning null signals that account could not be generated
		}

		@Override
		public boolean closeAccount(String number) {
			return false; // false signals unsuccessful closing
		}

		@Override
		public Set<String> getAccountNumbers() {
			return accounts.keySet();
		}

		@Override
		public Account getAccount(String number) {
			return accounts.get(number);
		}

		@Override
		public void transfer(Account a, Account b, double amount) {
			// since this bank only supports one account, transfer always transfers amount
			// from one to the same account, thus this empty implementation is correct!
		}
	}

	static class DummyAccount implements Account {
		private String owner = "Dagobert Duck";
		private String number = "DD-33-4499";
		private double balance;
		private boolean active = true;

		@Override
		public String getNumber() {
			return number;
		}

		@Override
		public String getOwner() {
			return owner;
		}

		@Override
		public boolean isActive() {
			return active;
		}

		@Override
		public void deposit(double amount) {
			balance += amount;
		}

		@Override
		public void withdraw(double amount) {
			balance -= amount;
		}

		@Override
		public double getBalance() {
			return balance;
		}

		@Override
		public boolean close() throws IOException {
			active = false;
			return true;
		}
	}

}
