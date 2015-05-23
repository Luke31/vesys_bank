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
import bank.driver.TransferData;
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
                Message requestM = consumer.receive(); //Load Request from Client - May not be too large!
                Request request = requestM.getBody(Request.class);
                Request answer = getRequestHandler().handleRequest(request); //Get ServerRequestHandler from parent-class
                
                sender.send(requestM.getJMSReplyTo(), answer);

                //Inform Publisher/Topic
                if(Request.RequestType.Account.equals(request.getType()) && (AccountRequestData.AccountRequestType.A_Deposit.equals(((AccountRequestData)request.getData()).getType()) || AccountRequestData.AccountRequestType.A_Withdraw.equals(((AccountRequestData)request.getData()).getType())))
                    publisher.send(topic, ((AccountRequestData)request.getData()).getAccountNumber());
                else if(Request.RequestType.Bank.equals(request.getType()) && BankRequestData.BankRequestType.Transfer.equals(((BankRequestData)request.getData()).getType())){
                    TransferData transferData = (TransferData)((BankRequestData)request.getData()).getData(); 
                    
                    publisher.send(topic, transferData.aNum);
                    try {
                        Thread.sleep(100); //FIXME: Sollte nicht notwendig sein - Jedoch ansonsten: Invalid concurrent session usage
                    } catch (InterruptedException e) {}
                    publisher.send(topic, transferData.bNum);
                }
            }
        }
	}

}