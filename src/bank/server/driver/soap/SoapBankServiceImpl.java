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
    public boolean closeAccount(String number) throws IOException {
        return bank.closeAccount(number);
    }
    
    @Override
    public Set<String> getAccountNumbers() throws IOException {
        return bank.getAccountNumbers();
    }

    @Override
    public void transfer(String aNumber, String bNumber, double amount)
            throws IOException, IllegalArgumentException, OverdrawException,
            InactiveException {
        bank.transfer (bank.getAccount(aNumber), bank.getAccount(bNumber), amount);
    }
    
    //Account
    @Override
    public String getOwner(@WebParam(name = "number") String number) throws IOException{
        Account acc = bank.getAccount(number);
        if(acc != null)
            return bank.getAccount(number).getOwner();
        else
            return null;
    }
    
    @Override
    public boolean isActive(String number) throws IOException {
        return bank.getAccount(number).isActive();
    }

    @Override
    public void deposit(String number, double amount) throws IOException,
            IllegalArgumentException, InactiveException {
        bank.getAccount(number).deposit(amount);
    }

    @Override
    public void withdraw(String number, double amount) throws IOException,
            IllegalArgumentException, OverdrawException, InactiveException {
        bank.getAccount(number).withdraw(amount);        
    }

    @Override
    public double getBalance(String number) throws IOException {
        return bank.getAccount(number).getBalance();
    }

}
