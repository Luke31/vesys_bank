package bank.server.driver.soap;

import java.io.IOException;
import java.util.Set;

import javax.jws.WebParam;
import javax.jws.WebService;

import bank.InactiveException;
import bank.OverdrawException;


@WebService
public interface SoapBankService{
    //Bank
    String createAccount(@WebParam(name = "owner") String owner) throws IOException;
    boolean closeAccount(@WebParam(name = "number") String number) throws IOException;
    Set<String> getAccountNumbers() throws IOException;
    void transfer(@WebParam(name = "aNumber") String aNumber, @WebParam(name = "bNumber") String bNumber, @WebParam(name = "amount") double amount)
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException;
    
    //Account
    String getOwner(@WebParam(name = "number") String number) throws IOException;
    boolean isActive(@WebParam(name = "number") String number) throws IOException;
    void deposit(@WebParam(name = "number") String number, @WebParam(name = "amount") double amount) 
            throws IOException, IllegalArgumentException, InactiveException;
    void withdraw(@WebParam(name = "number") String number, @WebParam(name = "amount") double amount) 
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException;
    double getBalance(@WebParam(name = "number") String number) 
            throws IOException;
}
