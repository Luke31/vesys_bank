package bank.server.driver.soap;

import java.io.IOException;
import java.util.concurrent.Executors;

import javax.xml.ws.Endpoint;

import bank.server.driver.ServerBankDriver;

public class SoapServerBankDriver extends ServerBankDriver {
    
    @Override
    public void startServer(String[] args) throws IOException{      
        int port = Integer.parseInt(args[0]);
        String path = args[1];
        
        //Import wenn Server läuft, von Projekt-ordner aus:
        //wsimport -keep -p bank.client.driver.soap.jaxws -d bin -s src http://localhost:1111/hs?wsdl 

        String url = "http://127.0.0.1:" + port + "/" + path;
        
        Endpoint endpoint = Endpoint.create(new SoapBankServiceImpl(this.getBank())); 
        endpoint.setExecutor(Executors.newFixedThreadPool(10)); 
        endpoint.publish(url); 
        
        System.out.println("service published");
        System.out.println("WSDL available at " + url + "?wsdl");
    }
}
    
