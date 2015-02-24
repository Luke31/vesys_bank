package bank.client.driver.socket;

import java.io.IOException;
import java.util.Set;

import bank.Account;
import bank.driver.socket.Request.RequestType;
import bank.driver.socket.TransferData;

public class SocketBank implements bank.Bank {
    private SocketHandler socketHandler = new SocketHandler();    

    @Override
    public synchronized String createAccount(String owner) throws IOException {
        return socketHandler.sendRequest (RequestType.CreateAccount, owner);
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        return socketHandler.sendRequest (RequestType.CloseAccount, number);
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        return socketHandler.sendRequest (RequestType.GetAccountNumbers);
    }
    
    @Override
    public Account getAccount(String number) throws IOException {
        Account servAcc = socketHandler.sendRequest (RequestType.GetAccount, number);
        return new SocketAccount(servAcc.getNumber());
    }

    @Override
    public synchronized void transfer(Account from, Account to, double amount) throws IOException {
        socketHandler.sendRequest (RequestType.Transfer, new TransferData(from, to, amount));
    }
}