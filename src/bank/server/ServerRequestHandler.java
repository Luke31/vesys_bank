package bank.server;

import java.io.IOException;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.driver.AccountRequestData;
import bank.driver.BankRequestData;
import bank.driver.Request;
import bank.driver.TransferData;
import bank.driver.Request.RequestType;

public class ServerRequestHandler {
    Bank bank;
    public ServerRequestHandler(Bank bank){
        this.bank = bank;
    }

    public Request handleRequest(Request request) {
        Request answer = null;
        try{
            Object answerData = handleRequestLocally(request); //Handle Request for answer to Client
            answer = new Request(request.getType(), answerData);                       
        }catch(Exception e){
            //IOException or invalid Action -> Send Exception to client
            answer = new Request(RequestType.ExceptionStatus, e); //Return Exception
        }
        return answer;
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
    private Object handleRequestLocally(Request request) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        
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
