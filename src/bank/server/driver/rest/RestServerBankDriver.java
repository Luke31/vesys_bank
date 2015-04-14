package bank.server.driver.rest;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import bank.server.driver.ServerBankDriver;


public class RestServerBankDriver extends ServerBankDriver {
    @Override
    public void startServer(String[] args) throws IOException{      

        final String baseUri = "http://" + args[0] + ":" + Integer.parseInt(args[1]);

        final ResourceConfig rc = new ResourceConfig().packages("bank.server.driver.rest.resources");
        
        System.out.println("Starting grizzly...");
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), rc);
        
        System.out.println(String.format("Jersey app started with WADL available at "
                                + "%sapplication.wadl\nTry out %smsg\nHit enter to stop it...",
                                baseUri, baseUri));

        System.in.read();
        httpServer.shutdown();
    }
}
