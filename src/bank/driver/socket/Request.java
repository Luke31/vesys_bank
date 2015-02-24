package bank.driver.socket;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = -6087436160950505537L;

    public enum RequestType {  
        //Bank:
        CreateAccount, 
        CloseAccount,
        GetAccountNumbers,
        GetAccount,
        Transfer,
        
        //Account:
        Account;
    }
    
    private RequestType type;
    private Object data;
    
    public Request (RequestType type, Object data) {
        this.type = type;
        this.data = data;
    }
    
    public RequestType getType() { 
        return type; 
    }
    
    public Object getData() { 
        return data; 
    }
}
