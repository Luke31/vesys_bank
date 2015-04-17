package bank.server.driver.rest.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import bank.InactiveException;

@Provider
public class InactiveExceptionMapper implements ExceptionMapper<InactiveException> {
    public Response toResponse(InactiveException ex) {
        return Response.status(Status.GONE).build(); //HTTP/1.1 410 Gone    
    }
}
