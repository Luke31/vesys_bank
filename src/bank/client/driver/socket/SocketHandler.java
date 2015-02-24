package bank.client.driver.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bank.driver.socket.Request;
import bank.driver.socket.Request.RequestType;
import bank.server.BankServer;

public class SocketHandler {
    private Socket socket_obj = null;
    private ObjectOutputStream out_obj = null;
    private ObjectInputStream in_obj = null;
    
    private Socket getSocket() throws IOException {
        if(socket_obj == null || socket_obj.isClosed()) {
            socket_obj = new Socket(BankServer.HOST, BankServer.PORT);
        }
        
        return socket_obj;
    }
    
    public ObjectOutputStream getOutputStream() throws IOException {
        if (out_obj == null) {
            Socket socket = getSocket();
            out_obj = new ObjectOutputStream(socket.getOutputStream());
        }
        
        return out_obj;
    }
    
    public ObjectInputStream getInputStream() throws IOException {
        if (in_obj == null) {
            Socket socket = getSocket();
            in_obj = new ObjectInputStream(socket.getInputStream());
        }
        
        return in_obj;
    }
    
    public <T> T sendRequest (RequestType type) throws IOException {
        return sendRequest (type, null);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T sendRequest (RequestType type, Object data) throws IOException {
        ObjectOutputStream out = getOutputStream();
        
        out.writeObject(new Request (type, data));
        out.flush();
        
        ObjectInputStream in = getInputStream();      
        T ret = null;
        
        try {
            ret = (T)in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return ret;
    }
    
}
