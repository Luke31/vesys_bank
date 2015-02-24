package bank.client.driver.socket;

import java.io.IOException;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.driver.socket.AccountRequestData;
import bank.driver.socket.AccountRequestData.AccountRequestType;
import bank.driver.socket.Request.RequestType;

public class SocketAccount implements Account {
    private SocketHandler socketHandler = new SocketHandler();
    
    private String number;
    
    public SocketAccount(String number) {
        this.number = number;
    }

    @Override
    public synchronized String getNumber() throws IOException {
        return socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_GetNumber, number));
    }

    @Override
    public synchronized String getOwner() throws IOException {
        return socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_GetOwner, number));
    }

    @Override
    public synchronized boolean isActive() throws IOException {
        return socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_IsActive, number));
    }

    @Override
    public synchronized void deposit(double amount) throws IOException {
        socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_Deposit, number, amount));
    }

    @Override
    public synchronized void withdraw(double amount) throws IOException,
            IllegalArgumentException, OverdrawException, InactiveException {
        socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_Withdraw, number, amount));
    }

    @Override
    public synchronized double getBalance() throws IOException {
        return socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_GetBalance, number));
    }

    @Override
    public synchronized boolean close() throws IOException {
        return socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_Close, number));
    }

}
