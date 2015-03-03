package bank.client.driver;

import java.io.IOException;

import bank.driver.socket.Request;
import bank.driver.socket.Request.RequestType;

public interface ClientRequestHandler {

    public abstract Request sendRequest(RequestType type, Object data)
            throws IOException;

}