package bank;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = -6087436160950505537L;

    public enum RequestType {        
        CreateAccount, 
        CloseAccount,
        GetAccountNumbers,
        GetAccount,
        Transfer;
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
