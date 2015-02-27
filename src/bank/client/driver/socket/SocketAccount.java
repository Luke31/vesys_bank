package bank.client.driver.socket;

import java.io.IOException;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.driver.socket.AccountRequestData;
import bank.driver.socket.AccountRequestData.AccountRequestType;
import bank.driver.socket.Request;
import bank.driver.socket.Request.RequestType;

public class SocketAccount implements Account {
    private SocketHandler socketHandler;
    
    private String number;
    
    public SocketAccount(String number, SocketHandler socketHandler) {
        this.number = number;
        this.socketHandler = socketHandler;
    }

    @Override
    public synchronized String getNumber() throws IOException {
        Request answer = socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_GetNumber, number));
        return (String)answer.getData(); 
    }

    @Override
    public synchronized String getOwner() throws IOException {
        return (String)socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_GetOwner, number)).getData();
    }

    @Override
    public synchronized boolean isActive() throws IOException {
        return (boolean)socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_IsActive, number)).getData();
    }

    @Override
    public synchronized void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
        Request answer = socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_Deposit, number, amount));
        if(answer.getType().equals(RequestType.ExceptionStatus)){
            Exception e = (Exception)answer.getData();
            if(e instanceof IllegalArgumentException)
                throw (IllegalArgumentException)e;
            else if(e instanceof InactiveException)
                throw (InactiveException)e;
        };
    }

    @Override
    public synchronized void withdraw(double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        Request answer = socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_Withdraw, number, amount));
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

    @Override
    public synchronized double getBalance() throws IOException {
        return (double)socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_GetBalance, number)).getData();
    }

    @Override
    public synchronized boolean close() throws IOException {
        return (boolean)socketHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_Close, number)).getData();
    }

}
