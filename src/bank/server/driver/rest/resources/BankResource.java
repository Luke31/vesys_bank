package bank.server.driver.rest.resources;

import java.io.IOException;
import java.util.Set;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bank.Account;
import bank.Bank;

@Singleton
@Path("/bank")
public class BankResource {
    private Bank bank;
    
    public BankResource(Bank bank) {
        this.bank = bank;
    }
    
    @POST
    @Produces("text/plain")
    public String createAccount(String owner) {
        try {
            return bank.createAccount(owner);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @PUT
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public boolean closeAccount(String number) {
        // TODO Auto-generated method stub
        return false;
    }

    @GET
    public Set<String> getAccountNumbers() {
        // TODO Auto-generated method stub
        return null;
    }

    @GET
    @Path("{number}")
    public Account getAccount(@PathParam("number") String number) {
        // TODO Auto-generated method stub
        return null;
    }

//    @PUT
//    public void transfer(Account a, Account b, double amount) {
//        // TODO Auto-generated method stub
//        
//    }

}
