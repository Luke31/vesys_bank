package bank.server.driver.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import bank.driver.Request;
import bank.server.driver.ServerBankDriver;

public class SocketServerBankDriver extends ServerBankDriver {
    private static int MAX_CLIENTS = 10;
    private ServerSocket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
	
	@Override
	public void startServer(String[] args) throws IOException{	    
	    try {
	        int port = Integer.parseInt(args[0]);
            socket = new ServerSocket(port);
            socket.setSoTimeout(60000);
            
            for(int i = 0; i < MAX_CLIENTS; i++) {
                Thread t = new Thread(new ConnectionHandler());
                t.start();
            }
        } catch (IOException e) {
            throw new IOException();
        } 
	}

	/* (non-Javadoc)
     * @see bank.server.driver.socket.ServerDriver#run()
     */
    public class ConnectionHandler implements Runnable{
        
        @Override
    	public void run() {
    		Socket client = null;
    
    		while (true) {
    			try {
                    client = socket.accept();
    
                    in = new ObjectInputStream(client.getInputStream());
                    out = new ObjectOutputStream(client.getOutputStream());
    
                    while (true) {  
                        Request request = (Request)in.readObject(); //Load Request from Client - May not be too large!
                        Request answer = getRequestHandler().handleRequest(request); //Get ServerRequestHandler from parent-class
                        out.writeObject(answer); //Answer to client
                        out.flush();                    
                    }
    			} catch (ClassCastException e){
    			    e.printStackTrace();
    			} catch (Exception e) {
    			    // ignore
                }		
    		}
    		
    		
    	}
    }

}