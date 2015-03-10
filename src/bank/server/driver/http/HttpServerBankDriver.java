package bank.server.driver.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

import bank.driver.Request;
import bank.driver.Request.RequestType;
import bank.server.driver.ServerBankDriver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpServerBankDriver extends ServerBankDriver {
    
    @Override
    public void startServer(String[] args) throws IOException{      
        try {
            int port = Integer.parseInt(args[0]);
            
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0); 
            
            server.createContext("/request", new RequestHandler()); 
            server.start(); 
        } catch (IOException e) {
            throw new IOException();
        }
    }

    private class RequestHandler implements HttpHandler {
        // content -length
        // content-type: text/plain
        @Override
        public void handle(HttpExchange exchange) throws IOException {   
            ObjectInputStream in = null;
            ObjectOutputStream out = null;
            
            try {
                in = new ObjectInputStream(exchange.getRequestBody());
                Request request = (Request)in.readObject();
                Request answer = getRequestHandler().handleRequest(request);
                int response = 200;
                
                if (answer.getType() == RequestType.ExceptionStatus) {
                    response = 400;
                }
                
                exchange.sendResponseHeaders(response, sizeof(answer));
                out = new ObjectOutputStream(exchange.getResponseBody());
                out.writeObject(answer);
                out.flush();
                
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }    
            }
        }
    }
    
    private int sizeof(Object obj) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);

        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        objectOutputStream.close();

        return byteOutputStream.toByteArray().length;
    }
}
    
