package bank.server.driver;

import java.io.IOException;

import bank.Bank;
import bank.server.BankImpl;
import bank.server.ServerRequestHandler;

public abstract class ServerBankDriver {
    private Bank bank = null;
    private ServerRequestHandler requestHandler;
    
    public ServerBankDriver(){
        this.bank = new BankImpl();
        this.requestHandler = new ServerRequestHandler(this.bank);
    }
    
    public ServerRequestHandler getRequestHandler() {
        return requestHandler;
    }
    
    public Bank getBank(){
        return bank;
    }

    public abstract void startServer(String[] args) throws IOException;

}