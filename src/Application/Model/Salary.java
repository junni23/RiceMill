package Application.Model;

/**
 * Created by Sohel on 5/31/2017.
 */
public class Salary {
    private int id;
    private String date;
    private String invoice_number;
    private String month_year;
    private Employee employee;
    private Account account;

    public Salary(int id, String date, String month_year, Employee employee) {
        this.id = id;
        this.date = date;
        this.month_year = month_year;
        this.employee = employee;
    }

    public Salary(Employee employee) {
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth_year() {
        return month_year;
    }

    public void setMonth_year(String month_year) {
        this.month_year = month_year;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
