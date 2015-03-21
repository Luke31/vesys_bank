package bank.client.driver.soap;

import java.io.IOException;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.client.driver.soap.jaxws.IOException_Exception;
import bank.client.driver.soap.jaxws.SoapBankServiceImpl;

public class SoapAccount implements Account {

    private SoapBankServiceImpl servicePort;    
    
    private String number;
    private String owner;
    
    public SoapAccount(String number, String owner, SoapBankServiceImpl servicePort) {
        super();
        this.number = number;
        this.owner = owner;
        this.servicePort = servicePort;
    }

    @Override
    public String getNumber() throws IOException {
        return number;
    }

    @Override
    public String getOwner() throws IOException {
        return owner;
    }

    @Override
    public boolean isActive() throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void deposit(double amount) throws IOException,
            IllegalArgumentException, InactiveException {
        // TODO Auto-generated method stub

    }

    @Override
    public void withdraw(double amount) throws IOException,
            IllegalArgumentException, OverdrawException, InactiveException {
        // TODO Auto-generated method stub

    }

    @Override
    public double getBalance() throws IOException {
        try {
            return servicePort.getBalance(number);
        } catch (IOException_Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
