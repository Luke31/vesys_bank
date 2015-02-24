package bank.client.driver.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

import bank.Account;
import bank.driver.socket.Request;
import bank.driver.socket.TransferData;
import bank.driver.socket.Request.RequestType;
import bank.server.BankServer;

public class SocketBank implements bank.Bank {

    private Socket socket_obj = null;
    private ObjectOutputStream out_obj = null;
    private ObjectInputStream in_obj = null;
    
    public Socket getSocket() throws IOException {
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
    
    private <T> T sendRequest (RequestType type) throws IOException {
        return sendRequest (type, null);
    }
    
    @SuppressWarnings("unchecked")
    private <T> T sendRequest (RequestType type, Object data) throws IOException {
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
    
    @Override
    public synchronized String createAccount(String owner) throws IOException {
        return sendRequest (RequestType.CreateAccount, owner);
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        return sendRequest (RequestType.CloseAccount, number);
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        return sendRequest (RequestType.GetAccountNumbers);
    }
    
    @Override
    public Account getAccount(String number) throws IOException {
        return sendRequest (RequestType.GetAccount, number);
    }

    @Override
    public synchronized void transfer(bank.Account from, bank.Account to, double amount) throws IOException {
        sendRequest (RequestType.Transfer, new TransferData(from, to, amount));
    }
}