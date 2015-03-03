package bank.client;

import java.io.IOException;

import bank.driver.socket.Request;
import bank.driver.socket.Request.RequestType;

public interface ClientHandler {

    public abstract Request sendRequest(RequestType type, Object data)
            throws IOException;

}