package bank.client;

import java.io.IOException;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.driver.socket.AccountRequestData;
import bank.driver.socket.AccountRequestData.AccountRequestType;
import bank.driver.socket.Request;
import bank.driver.socket.Request.RequestType;

public class ClientAccount implements Account {
    private ClientHandler clientHandler;
    
    private String number;
    
    public ClientAccount(String number, ClientHandler socketHandler) {
        this.number = number;
        this.clientHandler = socketHandler;
    }

    @Override
    public synchronized String getNumber() throws IOException {
        Request answer = clientHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_GetNumber, number));
        return (String)answer.getData(); 
    }

    @Override
    public synchronized String getOwner() throws IOException {
        return (String)clientHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_GetOwner, number)).getData();
    }

    @Override
    public synchronized boolean isActive() throws IOException {
        return (boolean)clientHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_IsActive, number)).getData();
    }

    @Override
    public synchronized void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
        Request answer = clientHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_Deposit, number, amount));
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
        Request answer = clientHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_Withdraw, number, amount));
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
        return (double)clientHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_GetBalance, number)).getData();
    }

    @Override
    public synchronized boolean close() throws IOException {
        return (boolean)clientHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_Close, number)).getData();
    }

}
