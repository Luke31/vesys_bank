package bank.client;

import java.io.IOException;
import java.util.Set;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.client.driver.ClientRequestHandler;
import bank.driver.AccountRequestData;
import bank.driver.AccountRequestData.AccountRequestType;
import bank.driver.BankRequestData;
import bank.driver.BankRequestData.BankRequestType;
import bank.driver.Request;
import bank.driver.Request.RequestType;
import bank.driver.TransferData;

public class ClientBank implements bank.Bank {
    private ClientRequestHandler clientRequestHandler;    

    public ClientBank(ClientRequestHandler clientHandler) {
        super();
        this.clientRequestHandler = clientHandler;
    }

    @Override
    public synchronized String createAccount(String owner) throws IOException {
        return (String)clientRequestHandler.sendRequest (RequestType.Bank, new BankRequestData(BankRequestType.CreateAccount, owner)).getData();
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        return (boolean)clientRequestHandler.sendRequest (RequestType.Bank, new BankRequestData(BankRequestType.CloseAccount, number)).getData();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<String> getAccountNumbers() throws IOException {
        return (Set<String>)clientRequestHandler.sendRequest (RequestType.Bank, new BankRequestData(BankRequestType.GetAccountNumbers, null)).getData();
    }
    
    @Override
    public Account getAccount(String number) throws IOException {
        Request answer = clientRequestHandler.sendRequest (RequestType.Account, new AccountRequestData(AccountRequestType.A_GetBalance, number));
        if(answer.getType().equals(RequestType.ExceptionStatus)){
            Exception e = (Exception)answer.getData();
            if(e instanceof NullPointerException)
                return null; //Account existiert nicht
            else
                throw new RuntimeException(); 
        }else
            return new ClientAccount(number, clientRequestHandler); //Account existiert
    }

    @Override
    public synchronized void transfer(Account from, Account to, double amount) throws IOException, IllegalArgumentException, OverdrawException,
    InactiveException {
        Request answer = clientRequestHandler.sendRequest (RequestType.Bank, new BankRequestData(BankRequestType.Transfer, new TransferData(from.getNumber(), to.getNumber(), amount)));
        if(answer.getType().equals(RequestType.ExceptionStatus)){
            Exception e = (Exception)answer.getData();
            if(e instanceof IllegalArgumentException)
                throw (IllegalArgumentException)e;
            else if(e instanceof InactiveException)
                throw (InactiveException)e;
            else if(e instanceof OverdrawException)
                throw (OverdrawException)e;
            else
                throw new RuntimeException();
        };
    }
}