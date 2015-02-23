package bank;

public class TransferData {
    public Account a;
    public Account b;
    public double amount;
    
    public TransferData (Account a, Account b, double amount) {
        this.a = a;
        this.b = b;
        this.amount = amount;
    }
}
