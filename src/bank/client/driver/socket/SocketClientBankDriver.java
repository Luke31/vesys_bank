/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.client.driver.socket;

import java.io.IOException;
import java.net.UnknownHostException;

import bank.Bank;
import bank.client.ClientBank;

public class SocketClientBankDriver implements bank.client.driver.ClientBankDriver {
	private Bank bank = null;
	private int port;
	private String host;
	
	@Override
	public void connect(String[] args) throws UnknownHostException, IOException {
	    this.host = args[0];
	    this.port = Integer.parseInt(args[1]);
	    
		bank = new ClientBank(new SocketClientRequestHandler(host, port)); //Proxy-Bank für GUI (Client)
		
		System.out.println("socket connected...");
	}

	@Override
	public void disconnect() {
		bank = null;
		 // XXX hier sollte noch der Socket geschlossen werden, z.B. durch aufruf einer Methode bank.close();
		System.out.println("socket disconnected...");
	}

	@Override
	public Bank getBank() {
		return bank;
	}
}