package bank.server.driver.rest.resources;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundMapper implements ExceptionMapper<InternalServerErrorException> {
    public Response toResponse(InternalServerErrorException ex) {
        return Response.status(404).
          entity(ex.getMessage()).
          type("text/plain").
          build(); //
      }
}
