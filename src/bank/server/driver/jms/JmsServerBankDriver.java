package bank.server.driver.jms;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

import bank.driver.AccountRequestData;
import bank.driver.BankRequestData;
import bank.driver.Request;
import bank.server.driver.ServerBankDriver;

public class JmsServerBankDriver extends ServerBankDriver {
	
	@Override
	public void startServer(String[] args) throws Exception{	    
	    final Context jndiContext = new InitialContext(); //Start HornetQ required
        final ConnectionFactory factory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
        final Queue queue = (Queue) jndiContext.lookup("/queue/BANK"); 
        final Topic topic = (Topic) jndiContext.lookup("/topic/BANK");
        
        try (JMSContext context = factory.createContext()) {
            JMSConsumer consumer = context.createConsumer(queue); //Eingabe Queue
            JMSProducer sender = context.createProducer(); //Antwort Queue (TempQueue von Client gegeben)
            JMSProducer publisher = context.createProducer(); //Ausgabe Topic

            while (true) {
                Message request = consumer.receive(); //Load Request from Client - May not be too large!
                Request answer = getRequestHandler().handleRequest(request.getBody(Request.class)); //Get ServerRequestHandler from parent-class
                
                sender.send(request.getJMSReplyTo(), answer);

                //Inform Publisher/Topic
                if(Request.RequestType.Account.equals(answer.getType()))
                    publisher.send(topic, ((AccountRequestData)answer.getData()).getAccountNumber());
                if(Request.RequestType.Bank.equals(answer.getType()) && BankRequestData.BankRequestType.Transfer.equals(((BankRequestData)answer.getData()).getType())){
                    //Todo aNum, bNum
                    publisher.send(topic, ((AccountRequestData)answer.getData()).getAccountNumber());
                    publisher.send(topic, ((AccountRequestData)answer.getData()).getAccountNumber());
                }
            }
        }
	}

}