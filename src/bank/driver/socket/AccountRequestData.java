package bank.driver.socket;

import java.io.Serializable;

public class AccountRequestData implements Serializable {
    
    private static final long serialVersionUID = 2881663028150634883L;

    public enum AccountRequestType{
        A_GetNumber,
        A_GetOwner,
        A_IsActive,
        A_Deposit,
        A_Withdraw,
        A_GetBalance,
        A_Close;
    }
    
    private AccountRequestType type;
    private String accNumber;
    private Double data;
    
    public AccountRequestData (AccountRequestType type, String accNumber) {
        this.type = type;
        this.accNumber = accNumber;
    }
    
    public AccountRequestData (AccountRequestType type, String accNumber, Double data) {
        this.type = type;
        this.accNumber = accNumber;
        this.data = data;
    }
    
    public AccountRequestType getType() { 
        return type; 
    }
    
    public String getAccountNumber(){
        return accNumber;
    }
    
    public Double getData() { 
        return data; 
    }
}
