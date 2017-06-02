package Application.Model;

/**
 * Created by Sohel on 6/2/2017.
 */
public class Transaction {
    private int id;
    private String invoiceNumber;
    private String description;
    private Account from;
    private Account to;
    private double amount;

    public Transaction(int id, String invoiceNumber, String description, Account from, Account to, double amount) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.description = description;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Transaction(String invoiceNumber, String description, Account from, Account to, double amount) {
        this.invoiceNumber = invoiceNumber;
        this.description = description;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
