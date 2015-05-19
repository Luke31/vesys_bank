/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.client.driver.jms;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import bank.Bank;
import bank.client.ClientBank;
import bank.client.driver.ClientRequestHandler;
import bank.driver.Request;
import bank.driver.Request.RequestType;

public class JmsClientBankDriver implements bank.client.driver.ClientBankDriver2 {
	private Bank bank = null;
	ConnectionFactory factory;
	Queue queue;
	Topic topic;
	JMSContext context;
	TemporaryQueue tempQueue;
	JMSProducer sender;
	JMSConsumer receiver;
	JMSConsumer subscriber;
	
	@Override
	public void connect(String[] args) throws UnknownHostException, IOException {
	    Context jndiContext;
        try {
            jndiContext = new InitialContext();
        
        factory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
        queue = (Queue) jndiContext.lookup("/queue/BANK"); 
        topic = (Topic) jndiContext.lookup("/topic/BANK");
        
        context = factory.createContext();
        tempQueue = context.createTemporaryQueue(); //Antwort-queue
        
        sender = context.createProducer().setJMSReplyTo(tempQueue); //Ausgabe Queue
        receiver = context.createConsumer(tempQueue);
	    
		bank = new ClientBank(new JmsClientRequestHandler()); //Proxy-Bank für GUI (Client)
		
		System.out.println("JMS \"connected\"...");
        } catch (Exception e) {
        }
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

	//Register updatehandler on serverside
    @Override
    public void registerUpdateHandler(UpdateHandler handler) throws IOException {
        subscriber = context.createConsumer(topic); //Eingabe Queue
        
        subscriber.setMessageListener((Message msg) -> {
            try {
                handler.accountChanged(msg.getBody(String.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    class JmsClientRequestHandler implements ClientRequestHandler {
        @Override
        public Request sendRequest (RequestType type, Object data) throws IOException {
            sender.send(queue, new Request (type, data)); //Send request to Server
            Request ret = receiver.receiveBody(Request.class); //Asynchron? - blockiert jedoch bis Message da
            
            return ret;
        }
    }
}