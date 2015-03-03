package bank.client.driver.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import bank.client.driver.ClientRequestHandler;
import bank.driver.socket.Request;
import bank.driver.socket.Request.RequestType;

public class SocketClientRequestHandler implements ClientRequestHandler {
    private Socket socket_obj = null;
    private ObjectOutputStream out_obj = null;
    private ObjectInputStream in_obj = null;
    private String host;
    private int port;
    
    public SocketClientRequestHandler(String host, int port) throws UnknownHostException, IOException {
        this.host = host;
        this.port = port;
    }

    private Socket getSocket() throws IOException {
        if(socket_obj == null || socket_obj.isClosed()) {
            socket_obj = new Socket(host, port);
        }
        
        return socket_obj;
    }

    private ObjectOutputStream getOutputStream() throws IOException {
        if (out_obj == null) {
            Socket socket = getSocket();
            out_obj = new ObjectOutputStream(socket.getOutputStream());
        }
        
        return out_obj;
    }

    private ObjectInputStream getInputStream() throws IOException {
        if (in_obj == null) {
            Socket socket = getSocket();
            in_obj = new ObjectInputStream(socket.getInputStream());
        }
        
        return in_obj;
    }
    
    /* (non-Javadoc)
     * @see bank.client.driver.socket.ClientHandler#sendRequest(bank.driver.socket.Request.RequestType, java.lang.Object)
     */
    @Override
    public Request sendRequest (RequestType type, Object data) throws IOException {
        ObjectOutputStream out = getOutputStream();
        
        out.writeObject(new Request (type, data)); //Send request to Server
        out.flush();
        
        ObjectInputStream in = getInputStream();
        Request ret = null;
        
        try {
            ret = (Request)in.readObject(); //Get response  
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
        
        return ret;
    }
    
}
