
package bank.client.driver.soap.jaxws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SoapBankServiceImpl", targetNamespace = "http://soap.driver.server.bank/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SoapBankServiceImpl {


    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @throws InactiveException_Exception
     * @throws IOException_Exception
     * @throws OverdrawException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "transfer", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.Transfer")
    @ResponseWrapper(localName = "transferResponse", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.TransferResponse")
    @Action(input = "http://soap.driver.server.bank/SoapBankServiceImpl/transferRequest", output = "http://soap.driver.server.bank/SoapBankServiceImpl/transferResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/transfer/Fault/IOException"),
        @FaultAction(className = OverdrawException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/transfer/Fault/OverdrawException"),
        @FaultAction(className = InactiveException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/transfer/Fault/InactiveException")
    })
    public void transfer(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        double arg2)
        throws IOException_Exception, InactiveException_Exception, OverdrawException_Exception
    ;

    /**
     * 
     * @param number
     * @return
     *     returns java.lang.String
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOwner", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.GetOwner")
    @ResponseWrapper(localName = "getOwnerResponse", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.GetOwnerResponse")
    @Action(input = "http://soap.driver.server.bank/SoapBankServiceImpl/getOwnerRequest", output = "http://soap.driver.server.bank/SoapBankServiceImpl/getOwnerResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/getOwner/Fault/IOException")
    })
    public String getOwner(
        @WebParam(name = "number", targetNamespace = "")
        String number)
        throws IOException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createAccount", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.CreateAccount")
    @ResponseWrapper(localName = "createAccountResponse", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.CreateAccountResponse")
    @Action(input = "http://soap.driver.server.bank/SoapBankServiceImpl/createAccountRequest", output = "http://soap.driver.server.bank/SoapBankServiceImpl/createAccountResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/createAccount/Fault/IOException")
    })
    public String createAccount(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws IOException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "closeAccount", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.CloseAccount")
    @ResponseWrapper(localName = "closeAccountResponse", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.CloseAccountResponse")
    @Action(input = "http://soap.driver.server.bank/SoapBankServiceImpl/closeAccountRequest", output = "http://soap.driver.server.bank/SoapBankServiceImpl/closeAccountResponse")
    public boolean closeAccount(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAccountNumbers", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.GetAccountNumbers")
    @ResponseWrapper(localName = "getAccountNumbersResponse", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.GetAccountNumbersResponse")
    @Action(input = "http://soap.driver.server.bank/SoapBankServiceImpl/getAccountNumbersRequest", output = "http://soap.driver.server.bank/SoapBankServiceImpl/getAccountNumbersResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/getAccountNumbers/Fault/IOException")
    })
    public List<String> getAccountNumbers()
        throws IOException_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @throws InactiveException_Exception
     * @throws IOException_Exception
     * @throws OverdrawException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "withdraw", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.Withdraw")
    @ResponseWrapper(localName = "withdrawResponse", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.WithdrawResponse")
    @Action(input = "http://soap.driver.server.bank/SoapBankServiceImpl/withdrawRequest", output = "http://soap.driver.server.bank/SoapBankServiceImpl/withdrawResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/withdraw/Fault/IOException"),
        @FaultAction(className = OverdrawException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/withdraw/Fault/OverdrawException"),
        @FaultAction(className = InactiveException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/withdraw/Fault/InactiveException")
    })
    public void withdraw(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        double arg1)
        throws IOException_Exception, InactiveException_Exception, OverdrawException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns double
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBalance", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.GetBalance")
    @ResponseWrapper(localName = "getBalanceResponse", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.GetBalanceResponse")
    @Action(input = "http://soap.driver.server.bank/SoapBankServiceImpl/getBalanceRequest", output = "http://soap.driver.server.bank/SoapBankServiceImpl/getBalanceResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/getBalance/Fault/IOException")
    })
    public double getBalance(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws IOException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "isActive", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.IsActive")
    @ResponseWrapper(localName = "isActiveResponse", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.IsActiveResponse")
    @Action(input = "http://soap.driver.server.bank/SoapBankServiceImpl/isActiveRequest", output = "http://soap.driver.server.bank/SoapBankServiceImpl/isActiveResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/isActive/Fault/IOException")
    })
    public boolean isActive(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws IOException_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @throws InactiveException_Exception
     * @throws IOException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "deposit", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.Deposit")
    @ResponseWrapper(localName = "depositResponse", targetNamespace = "http://soap.driver.server.bank/", className = "bank.client.driver.soap.jaxws.DepositResponse")
    @Action(input = "http://soap.driver.server.bank/SoapBankServiceImpl/depositRequest", output = "http://soap.driver.server.bank/SoapBankServiceImpl/depositResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/deposit/Fault/IOException"),
        @FaultAction(className = InactiveException_Exception.class, value = "http://soap.driver.server.bank/SoapBankServiceImpl/deposit/Fault/InactiveException")
    })
    public void deposit(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        double arg1)
        throws IOException_Exception, InactiveException_Exception
    ;

}
