package Application.TableModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Sohel on 6/2/2017.
 */
public class SalaryData {
    private SimpleIntegerProperty id;
    private SimpleStringProperty date;
    private SimpleIntegerProperty employee_id;
    private SimpleStringProperty paid_month_year;
    private SimpleDoubleProperty amount;

    public SalaryData(int id, String date, int employee_id, String paid_month_year, double amount) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date);
        this.employee_id = new SimpleIntegerProperty(employee_id);
        this.paid_month_year = new SimpleStringProperty(paid_month_year);
        this.amount = new SimpleDoubleProperty(amount);
    }

    public SalaryData(String date, int employee_id, String paid_month_year, double amount) {
       this(-1,date,employee_id,paid_month_year,amount);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public int getEmployee_id() {
        return employee_id.get();
    }

    public SimpleIntegerProperty employee_idProperty() {
        return employee_id;
    }

    public String getPaid_month_year() {
        return paid_month_year.get();
    }

    public SimpleStringProperty paid_month_yearProperty() {
        return paid_month_year;
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }
}
