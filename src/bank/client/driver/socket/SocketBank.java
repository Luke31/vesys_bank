package bank.client.driver.socket;

import java.io.IOException;
import java.util.Set;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.driver.socket.BankRequestData;
import bank.driver.socket.BankRequestData.BankRequestType;
import bank.driver.socket.Request;
import bank.driver.socket.Request.RequestType;
import bank.driver.socket.TransferData;

public class SocketBank implements bank.Bank {
    private SocketHandler socketHandler = new SocketHandler();    

    @Override
    public synchronized String createAccount(String owner) throws IOException {
        return (String)socketHandler.sendRequest (RequestType.Bank, new BankRequestData(BankRequestType.CreateAccount, owner)).getData();
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        return (boolean)socketHandler.sendRequest (RequestType.Bank, new BankRequestData(BankRequestType.CloseAccount, number)).getData();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<String> getAccountNumbers() throws IOException {
        return (Set<String>)socketHandler.sendRequest (RequestType.Bank, new BankRequestData(BankRequestType.GetAccountNumbers, null)).getData();
    }
    
    @Override
    public Account getAccount(String number) throws IOException {
        Account servAcc = (Account)socketHandler.sendRequest (RequestType.Bank, new BankRequestData(BankRequestType.GetAccount, number)).getData();
        if (servAcc == null)
            return null;
        else
            return new SocketAccount(servAcc.getNumber(), socketHandler);
    }

    @Override
    public synchronized void transfer(Account from, Account to, double amount) throws IOException, IllegalArgumentException, OverdrawException,
    InactiveException {
        Request answer = socketHandler.sendRequest (RequestType.Bank, new BankRequestData(BankRequestType.Transfer, new TransferData(from.getNumber(), to.getNumber(), amount)));
        if(answer.getType().equals(RequestType.ExceptionStatus)){
            Exception e = (Exception)answer.getData();
            if(e instanceof IllegalArgumentException)
                throw (IllegalArgumentException)e;
            else if(e instanceof InactiveException)
                throw (InactiveException)e;
            else if(e instanceof OverdrawException)
                throw (OverdrawException)e;
        };
    }
}