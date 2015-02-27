package bank.server.driver.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.driver.socket.AccountRequestData;
import bank.driver.socket.BankRequestData;
import bank.driver.socket.Request;
import bank.driver.socket.Request.RequestType;
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
                    Request request = (Request)in.readObject(); //Load Request from Client - May not be too large!
                    
                    Request answer = null;
                    try{
                        Object answerData = handleRequest(request); //Handle Request for answer to Client
                        answer = new Request(request.getType(), answerData);                       
                    }catch(Exception e){
                        //IOException or invalid Action -> Send Exception to client
                        answer = new Request(RequestType.ExceptionStatus, e); //Return Exception
                    }
                    out.writeObject(answer); //Answer to client
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
		    case Bank:
		        BankRequestData bData = (BankRequestData)request.getData();
		        switch(bData.getType()){
        		    //Bank
        		    case CreateAccount: 
        		        return bank.createAccount((String)bData.getData());
        		   
        		    case CloseAccount:
        		        return bank.closeAccount((String)bData.getData());
        		        
        		    case GetAccountNumbers:
        		        return bank.getAccountNumbers();
        		        
        		    case GetAccount:
        		        return bank.getAccount((String)bData.getData());
        		    
        		    case Transfer:
        		        TransferData data = (TransferData)bData.getData();
        		        bank.transfer(bank.getAccount(data.aNum), bank.getAccount(data.bNum), data.amount);
        		        break;
		        }
		        break;
		    //Account
		    case Account:
		        AccountRequestData aData = (AccountRequestData)request.getData();
		        Account acc = bank.getAccount(aData.getAccountNumber());
		        switch(aData.getType()){
        		    case A_GetNumber:
        		        return acc.getNumber();
        		    case A_GetOwner:
        		        return acc.getOwner();
        		    case A_IsActive:
                        return acc.isActive();
        		    case A_Deposit:
                        acc.deposit(aData.getData()); break;
        		    case A_Withdraw:
                        acc.withdraw(aData.getData()); break;
        		    case A_GetBalance:
                        return acc.getBalance();
        		    case A_Close:
        		        return acc.close();
        		    default:
        		        break;
		        }
		        break;
		    default: 
		        break;
		}
		
        return null;
	}
}