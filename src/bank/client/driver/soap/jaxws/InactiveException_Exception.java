
package bank.client.driver.soap.jaxws;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "InactiveException", targetNamespace = "http://soap.driver.server.bank/")
public class InactiveException_Exception
    extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = -590793412727181092L;
    
    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private InactiveException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public InactiveException_Exception(String message, InactiveException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public InactiveException_Exception(String message, InactiveException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: bank.client.driver.soap.jaxws.InactiveException
     */
    public InactiveException getFaultInfo() {
        return faultInfo;
    }

}
