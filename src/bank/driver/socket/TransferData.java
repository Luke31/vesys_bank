package bank.driver.socket;

import java.io.Serializable;

public class TransferData implements Serializable{

    private static final long serialVersionUID = 6706803283972641531L;
    
    public String aNum;
    public String bNum;
    public double amount;
    
    public TransferData (String aNum, String bNum, double amount) {
        this.aNum = aNum;
        this.bNum = bNum;
        this.amount = amount;
    }
}
