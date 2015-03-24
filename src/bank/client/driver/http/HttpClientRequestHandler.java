package bank.client.driver.http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import bank.client.driver.ClientRequestHandler;
import bank.driver.Request;
import bank.driver.Request.RequestType;

public class HttpClientRequestHandler implements ClientRequestHandler {
    
    private URL url;
    
    public HttpClientRequestHandler(String host, int port) throws UnknownHostException, IOException {
        url = new URL ("http", host, port, "/request");
    }
    
    @Override
    public Request sendRequest(RequestType type, Object data) throws IOException {
        URLConnection connection = url.openConnection();    
        connection.setDoOutput(true);
        connection.setRequestProperty("transfer-coding","chunked");
        
        ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
        out.writeObject(new Request (type, data)); //Send request to Server
        out.flush();
        
        Request ret = null;
        
        try {
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            ret = (Request)in.readObject(); //Get response  
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
        
        return ret;
    }

}
