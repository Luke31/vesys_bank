/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.server.driver;

import bank.Bank;
import bank.server.BankImpl;

public class SocketDriver implements bank.driver.BankDriver {
	private Bank bank = null;

	@Override
	public void connect(String[] args) {
		bank = new BankImpl();
		System.out.println("local connected...");
	}

	@Override
	public void disconnect() {
		bank = null;
		System.out.println("local disconnected...");
	}

	@Override
	public Bank getBank() {
		return bank;
	}
}
