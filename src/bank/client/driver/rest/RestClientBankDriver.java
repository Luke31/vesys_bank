package bank.client.driver.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import bank.Bank;

public class RestClientBankDriver implements bank.client.driver.ClientBankDriver {
    private Bank bank = null;
    private URI uriBank = null;
    
    @Override
    public void connect(String[] args) throws UnknownHostException, IOException {
        try {
            uriBank = new URI("http://" + args[0] + ":" + Integer.parseInt(args[1]) + "/bank");
        } catch (NumberFormatException | URISyntaxException e) {
            e.printStackTrace();
        }
        
        Client c = ClientBuilder.newClient();
        //c.register(XStreamProvider.class);
        WebTarget r = c.target(uriBank);
        
        bank = new RestBank(r);
        
        System.out.println("rest connected...");
    }

    @Override
    public void disconnect() {
        bank = null;
        System.out.println("rest disconnected...");
    }

    @Override
    public Bank getBank() {
        return bank;
    }
}