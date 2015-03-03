package bank.server;

import java.io.IOException;

import bank.client.Client;
import bank.server.driver.ServerBankDriver;

public class BankServer {
	
	public static void main(String[] args) {
	    if (args.length < 1) {
            System.out.println("Usage: java " + Client.class.getName() + " <class>");
            System.exit(1);
        }
	    
        ServerBankDriver server = null;
        try {
            Class<?> c = Class.forName(args[0]);
            server = (ServerBankDriver) c.newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("class " + args[0] + " coult not be found");
            System.exit(1);
        } catch (InstantiationException e) {
            System.out.println("class " + args[0] + " could not be instantiated");
            System.out.println("probably it has no public default constructor!");
            System.exit(1);
        } catch (IllegalAccessException e) {
            System.out.println("class " + args[0] + " could not be instantiated");
            System.out.println("probably it is not declared public!");
            System.exit(1);
        }

        String[] serverArgs = new String[args.length - 1];
        System.arraycopy(args, 1, serverArgs, 0, args.length - 1);
	    
        try {
            server.startServer(serverArgs);
        } catch (IOException e) {
            System.out.println("Problem while starting the server:");
            e.printStackTrace();
            System.exit(1);
        }
	}

}
