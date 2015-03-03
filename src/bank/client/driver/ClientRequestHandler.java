package bank.client.driver;

import java.io.IOException;

import bank.driver.Request;
import bank.driver.Request.RequestType;

public interface ClientRequestHandler {

    public abstract Request sendRequest(RequestType type, Object data)
            throws IOException;

}