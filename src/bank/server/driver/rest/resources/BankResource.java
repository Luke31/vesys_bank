package bank.server.driver.rest.resources;

import java.io.IOException;
import java.net.URI;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import bank.Bank;
import bank.InactiveException;
import bank.server.AccountImpl;

@Singleton
@Path("/bank/accounts")
public class BankResource {
    private static Bank bank;
    
    public static void setBank(Bank bank){
        BankResource.bank = bank;
    }
    
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public String[] getAccountNumbers() {
        try {
            String[] d = bank.getAccountNumbers().toArray(new String[]{});
            return d;
        } catch (IOException e) {
            return null;
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createAccount(@Context UriInfo uriInfo,
            @FormParam("owner") String owner) {
        try {
            String number = bank.createAccount(owner);
            URI location = uriInfo.getAbsolutePathBuilder().path("" + number).build();
            return Response.created(location).entity(number).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }
    
    
    //accounts/{number}
    @GET
    @Path("{number}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountImpl getAccount(
            @PathParam("number") String number) throws IOException {
        AccountImpl acc = (AccountImpl)bank.getAccount(number);
        if(acc != null)
            return acc;
        else
            throw new InternalServerErrorException("Account not found");
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{number}")
    public Response updateBalance(@PathParam("number") String number, @FormParam("amount")  double amount) throws IOException,
    IllegalArgumentException, InactiveException{
        return Response.ok().build();
    }
    
    @DELETE
    @Path("{number}")
    @Produces({ MediaType.APPLICATION_JSON })
    public boolean closeAccount(@PathParam("number") String number) {
        // TODO Auto-generated method stub
        return false;
    }

    @HEAD
    @Path("{number}")
    public void checkActive(@PathParam("number") String number){
        
    }

    

//    @PUT
//    public void transfer(Account a, Account b, double amount) {
//        // TODO Auto-generated method stub
//        
//    }

}
