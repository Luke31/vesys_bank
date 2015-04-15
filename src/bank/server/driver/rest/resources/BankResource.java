package bank.server.driver.rest.resources;

import java.io.IOException;
import java.net.URI;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import bank.Account;
import bank.Bank;

@Singleton
@Path("/bank")
public class BankResource {
    private static Bank bank;
    
    public static void setBank(Bank bank){
        BankResource.bank = bank;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createAccount(@Context UriInfo uriInfo, String owner) {
        try {
            String number = bank.createAccount(owner);
            URI location = uriInfo.getAbsolutePathBuilder().path("" + number).build();
            return Response.created(location).entity(number).build();
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
    @Produces({ MediaType.APPLICATION_JSON })
    public String[] getAccountNumbers() {
        try {
            String[] d = bank.getAccountNumbers().toArray(new String[]{});
            return d;
        } catch (IOException e) {
            //Ignore
        }
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
