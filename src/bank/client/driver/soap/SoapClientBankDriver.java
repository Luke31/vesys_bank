package bank.client.driver.soap;

import java.io.IOException;
import java.net.UnknownHostException;

import bank.Bank;
import bank.client.driver.soap.jaxws.SoapBankServiceImplService;

public class SoapClientBankDriver implements bank.client.driver.ClientBankDriver {
    private Bank bank = null;
    private int port;
    private String host;
    
    @Override
    public void connect(String[] args) throws UnknownHostException, IOException {
        this.host = args[0];
        this.port = Integer.parseInt(args[1]);
        
        SoapBankServiceImplService service = new SoapBankServiceImplService(); //TODO: Hier sollte host und port übergeben werden
        bank = new SoapBank(service.getSoapBankServiceImplPort());
        
        System.out.println("soap connected...");
    }

    @Override
    public void disconnect() {
        bank = null;
        System.out.println("soap disconnected...");
    }

    @Override
    public Bank getBank() {
        return bank;
    }
}