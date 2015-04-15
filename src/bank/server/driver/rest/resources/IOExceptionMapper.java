package bank.server.driver.rest.resources;

import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IOExceptionMapper implements ExceptionMapper<IOException> {
    public Response toResponse(IOException ex) {
        return Response.status(404).
          entity(ex.getMessage()).
          type("text/plain").
          build();
      }
}
