package bank.server.driver.soap;

import java.io.IOException;
import java.util.Set;

import javax.jws.WebParam;
import javax.jws.WebService;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

@WebService
public class SoapBankServiceImpl implements SoapBankService {
    private Bank bank;

    public SoapBankServiceImpl(Bank bank) {
        super();
        this.bank = bank;
    }
    
    //Bank
    @Override
    public String createAccount(String owner) throws IOException{
        return bank.createAccount(owner);
    }

    @Override
    public boolean closeAccount(String number) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Set<String> getAccountNumbers() throws IOException {
        return bank.getAccountNumbers();
    }

    @Override
    public void transfer(String aNumber, String bNumber, double amount)
            throws IOException, IllegalArgumentException, OverdrawException,
            InactiveException {
        // TODO Auto-generated method stub
        
    }
    
    //Account
    Account acc;
    @Override
    public String getOwner(@WebParam(name = "number") String number) throws IOException{
        acc = bank.getAccount(number);
        return acc.getOwner();
    }
    
    @Override
    public boolean isActive(String number) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void deposit(String number, double amount) throws IOException,
            IllegalArgumentException, InactiveException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void withdraw(String number, double amount) throws IOException,
            IllegalArgumentException, OverdrawException, InactiveException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getBalance(String number) throws IOException {
        acc = bank.getAccount(number);
        return acc.getBalance();
    }

}
