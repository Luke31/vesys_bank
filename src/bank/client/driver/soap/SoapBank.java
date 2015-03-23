package bank.client.driver.soap;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.client.driver.soap.jaxws.IOException_Exception;
import bank.client.driver.soap.jaxws.SoapBankServiceImpl;

public class SoapBank implements bank.Bank {

    private SoapBankServiceImpl servicePort;
    
    public SoapBank(SoapBankServiceImpl servicePort) {
        super();
        this.servicePort = servicePort;
    }

    @Override
    public String createAccount(String owner) throws IOException {
        try {
            return servicePort.createAccount(owner);
        } catch (IOException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        return servicePort.closeAccount(number);
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        try {
            List<String> list = servicePort.getAccountNumbers(); //JAXB kennt Set<String> nicht unds macht daraus eine Liste
            return list.stream().collect(Collectors.toSet()); //Liste wieder zu Set konvertieren
        } catch (IOException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account getAccount(String number) throws IOException {
        try {
            String owner = servicePort.getOwner(number);
            return new SoapAccount(number, owner, servicePort);
        } catch (IOException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void transfer(Account a, Account b, double amount)
            throws IOException, IllegalArgumentException, OverdrawException,
            InactiveException {
        // TODO Auto-generated method stub
        
    }

}
