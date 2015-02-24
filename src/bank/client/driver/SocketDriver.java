/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.client.driver;

import java.io.IOException;
import java.net.UnknownHostException;

import bank.Bank;
import bank.client.driver.socket.SocketBank;

public class SocketDriver implements bank.driver.BankDriver {
	private Bank bank = null;

	@Override
	public void connect(String[] args) throws UnknownHostException, IOException {
		bank = new SocketBank(); //Proxy-Bank für GUI (Client)
		
		System.out.println("socket connected...");
	}

	@Override
	public void disconnect() {
		bank = null;
		System.out.println("socket disconnected...");
	}

	@Override
	public Bank getBank() {
		return bank;
	}
}