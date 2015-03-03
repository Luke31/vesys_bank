package bank.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import bank.driver.socket.Request;
import bank.driver.socket.Request.RequestType;

public interface ClientHandler {

    public abstract ObjectOutputStream getOutputStream() throws IOException;

    public abstract ObjectInputStream getInputStream() throws IOException;

    public abstract Request sendRequest(RequestType type, Object data)
            throws IOException;

}