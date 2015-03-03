package bank.server.driver.http;

import java.io.IOException;

import bank.driver.Request;
import bank.server.driver.ServerBankDriver;

public class HttpServerBankDriver extends ServerBankDriver {

    public HttpServerBankDriver(){
        super(); //Create ServerDriver with BankImpl and ServerRequestHandler
    }
    
    @Override
    public void startServer(String[] args) throws IOException {
        // Example to get Server RequestHandler:
        Request request = null;
        Request answer = getRequestHandler().handleRequest(request); // Get ServerRequestHandler from parent-class
    }

}
