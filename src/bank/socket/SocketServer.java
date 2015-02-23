package bank.socket;

import java.io.IOException;
import java.net.ServerSocket;

import bank.Bank;
import bank.BankImpl;

public class SocketServer {

	private static int MAX_CLIENTS = 10;	
	public static int PORT = 1111;
	public static String HOST = "localhost";
	private Bank bank = null;
	
	public static void main(String[] args) {
		new SocketServer(PORT);
	}
	
	public SocketServer (int port) {
		bank = new BankImpl();
		
		try {
			ServerSocket socket = new ServerSocket(port);
			socket.setSoTimeout(60000);
			
			for(int i = 0; i < MAX_CLIENTS; i++) {
				Thread t = new Thread(new SocketRequestHandler(bank, socket));
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
