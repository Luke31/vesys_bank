package bank.server.driver;

import java.io.IOException;

import bank.Bank;
import bank.server.BankImpl;

public abstract class ServerBankDriver {
    private Bank bank = null;
    
    public ServerBankDriver(){
        this.bank = new BankImpl();
    }
    
    public Bank getBank() {
        return bank;
    }    

    public abstract void startServer(String[] args) throws IOException;

}