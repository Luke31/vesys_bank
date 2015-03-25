package bank.client.driver.soap;

import java.io.IOException;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.client.driver.soap.jaxws.IOException_Exception;
import bank.client.driver.soap.jaxws.InactiveException_Exception;
import bank.client.driver.soap.jaxws.OverdrawException_Exception;
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
        try {
            return servicePort.isActive(number);
        } catch (IOException_Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public void deposit(double amount) throws IOException,
            IllegalArgumentException, InactiveException {
        try {
            servicePort.deposit(number,  amount);
        } catch (IOException_Exception e) {
            throw new IOException(e);
        } catch (InactiveException_Exception e) {
            throw new InactiveException(e);
        }

    }

    @Override
    public void withdraw(double amount) throws IOException,
            IllegalArgumentException, OverdrawException, InactiveException {
        try {
            servicePort.withdraw(number, amount);
        } catch (IOException_Exception e) {
            throw new IOException(e);
        } catch (InactiveException_Exception e) {
            throw new InactiveException(e);
        } catch (OverdrawException_Exception e) {
            throw new OverdrawException(e);
        }

    }

    @Override
    public double getBalance() throws IOException {
        try {
            return servicePort.getBalance(number);
        } catch (IOException_Exception e) {
            throw new IOException(e);
        }
    }

}
