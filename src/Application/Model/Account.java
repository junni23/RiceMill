package Application.Model;

/**
 * Created by Sohel on 6/1/2017.
 */
public class Account {

    private int id;
    private String accountName;
    private String address;
    private String contact;
    private String email;
    private int account_type;// 0 Real, 1 Personnal, 2 Nominal, 3 Bank
    private int is_load_account; // Default -1


    public Account(int id, String accountName, String address, String contact, String email, int account_type, int is_load_account) {
        this.id = id;
        this.accountName = accountName;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.account_type = account_type;
        this.is_load_account = is_load_account;
    }

    public Account(String accountName, String address, String contact, String email, int account_type, int is_load_account) {
        this.accountName = accountName;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.account_type = account_type;
        this.is_load_account = is_load_account;
    }

    public Account(String accountName, int account_type) {
        this(accountName,"","","",account_type,-1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccount_type() {
        return account_type;
    }

    public void setAccount_type(int account_type) {
        this.account_type = account_type;
    }

    public int getIs_load_account() {
        return is_load_account;
    }

    public void setIs_load_account(int is_load_account) {
        this.is_load_account = is_load_account;
    }

    @Override
    public String toString() {
        return getAccountName();
    }
}
