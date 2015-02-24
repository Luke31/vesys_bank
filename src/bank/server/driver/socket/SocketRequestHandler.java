package bank.server.driver.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.driver.socket.Request;
import bank.driver.socket.TransferData;

public class SocketRequestHandler implements Runnable {

	private ServerSocket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Bank bank;
	
	public SocketRequestHandler(Bank bank, ServerSocket socket) {
		this.bank = bank;
		this.socket = socket;
	}

	@Override
	public void run() {
		Socket client = null;

		while (true) {
			try {
                client = socket.accept();

                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());

                while (true) {  
                    Request request = (Request)in.readObject();
                    
                    out.writeObject(handleRequest(request));    
                    out.flush();                    
                }
			} catch (Exception e) {
			    // ignore
            }		
		}
		
		
	}
	
	/**
	 * Handle each type of possible request.
	 * 
	 * @param request The request sent by the client.
	 * @return The answer to be sent back to the client.
	 * @throws IOException
	 * @throws InactiveException 
	 * @throws OverdrawException 
	 * @throws IllegalArgumentException 
	 */
	private Object handleRequest(Request request) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
		switch (request.getType()) {
		    case CreateAccount: 
		        return bank.createAccount((String)request.getData());
		   
		    case CloseAccount:
		        return bank.closeAccount((String)request.getData());
		        
		    case GetAccountNumbers:
		        return bank.getAccountNumbers();
		        
		    case GetAccount:
		        return bank.getAccount((String)request.getData());
		    
		    case Transfer:
		        TransferData data = (TransferData)request.getData();
		        bank.transfer(data.a, data.b, data.amount);
		        
		    default: 
		        break;
		}
		
        return null;
	}
}