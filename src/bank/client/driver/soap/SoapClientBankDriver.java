package bank.client.driver.soap;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;

import bank.Bank;
import bank.client.driver.soap.jaxws.SoapBankServiceImplService;

public class SoapClientBankDriver implements bank.client.driver.ClientBankDriver {
    private Bank bank = null;
    private URL url = null;
    
    @Override
    public void connect(String[] args) throws UnknownHostException, IOException {
        url = new URL("http://" + args[0] + ":" + Integer.parseInt(args[1]) + "/" + args[2] + "?wsdl");
        
        SoapBankServiceImplService service = new SoapBankServiceImplService(url);
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