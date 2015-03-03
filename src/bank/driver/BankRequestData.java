package bank.driver;

import java.io.Serializable;

public class BankRequestData implements Serializable {

    private static final long serialVersionUID = -6087436160950505537L;

    public enum BankRequestType {  
        //Bank:
        CreateAccount, 
        CloseAccount,
        GetAccountNumbers,
        GetAccount,
        Transfer,
    }
    
    private BankRequestType type;
    private Object data;
    
    public BankRequestData (BankRequestType type, Object data) {
        this.type = type;
        this.data = data;
    }
    
    public BankRequestType getType() { 
        return type; 
    }
    
    public Object getData() { 
        return data; 
    }
}