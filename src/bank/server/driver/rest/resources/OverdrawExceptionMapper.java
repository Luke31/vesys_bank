package bank.server.driver.rest.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import bank.OverdrawException;

@Provider
public class OverdrawExceptionMapper implements ExceptionMapper<OverdrawException> {
    public Response toResponse(OverdrawException ex) {
        return Response.status(418).build(); //HTTP/1.1 418
    }
}
