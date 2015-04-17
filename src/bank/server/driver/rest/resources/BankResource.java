package bank.server.driver.rest.resources;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
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
    public Response getAccountNumbers(@Context Request request) throws IOException {
        String[] d = bank.getAccountNumbers().toArray(new String[]{});
        EntityTag etag = new EntityTag(""+Arrays.hashCode(d));
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(1200);
        
        Response.ResponseBuilder rb = request.evaluatePreconditions(etag);
        if (rb != null) //Cached entity found!
            return rb.cacheControl(cacheControl).tag(etag).build(); //HTTP/1.1 304 Not Modified
        else //No cache found -> Return Array
            return Response.ok(d).cacheControl(cacheControl).tag(etag).build(); 
            //HTTP/1.1 200 OK (with entity)
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(@Context UriInfo uriInfo,
        @FormParam("owner") String owner) throws IOException {
        String number = bank.createAccount(owner);
        URI location = uriInfo.getAbsolutePathBuilder().path("" + number).build();
        return Response.created(location).entity(number).build(); //HTTP/1.1 201 Created
    }
    
    @GET
    @Path("{number}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountImpl getAccount(
            @PathParam("number") String number) throws IOException {
        AccountImpl acc = (AccountImpl)bank.getAccount(number);
        return acc;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{number}")
    public Response updateBalance(@PathParam("number") String number, @FormParam("newBalance")  
    double newBalance) throws IOException,
    IllegalArgumentException, InactiveException, OverdrawException{
        Account acc = bank.getAccount(number);
        if(acc == null)
            throw new NotFoundException("Account not found"); //HTTP/1.1 404 Not Found
        
        double diff = newBalance - acc.getBalance();
        
        if(diff > 0)
            acc.deposit(diff); 
        else if(diff < 0)
            acc.withdraw(Math.abs(diff));
        else if(diff == 0){
            //Idempotence-feature or amount really is zero 
        }
        return Response.ok().build(); //HTTP/1.1 200 OK
    }
    
    @DELETE
    @Path("{number}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response closeAccount(@PathParam("number") String number) throws IOException {
        Account acc = bank.getAccount(number);
        if(acc == null)
            throw new NotFoundException("Account not found"); //HTTP/1.1 404 Not Found
        if (acc.isActive()){
            if(bank.closeAccount(acc.getNumber()))
                return Response.ok().build(); //HTTP/1.1 200 OK
            else
                return Response.serverError().build(); //HTTP/1.1 500 Internal Server Error
        }else {
            return Response.notModified().build(); //HTTP/1.1 304 Not Modified
        }
    }

    @HEAD
    @Path("{number}")
    public Response checkActive(@PathParam("number") String number) throws IOException{
        Account acc = bank.getAccount(number);
        if(acc == null)
            throw new NotFoundException("Account not found"); //HTTP/1.1 404 Not Found
        else if (acc.isActive())
            return Response.ok().build(); //HTTP/1.1 200 OK
        else 
            return Response.status(Status.GONE).build(); //HTTP/1.1 410 Gone    
    }
}
