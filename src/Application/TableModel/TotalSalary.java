package Application.TableModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Sohel on 6/4/2017.
 */
public class TotalSalary {
    private SimpleStringProperty name;
    private SimpleDoubleProperty totalSalary;

    public TotalSalary(String name, double totalSalary) {
        this.name = new SimpleStringProperty(name);
        this.totalSalary = new SimpleDoubleProperty(totalSalary);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public double getTotalSalary() {
        return totalSalary.get();
    }

    public SimpleDoubleProperty totalSalaryProperty() {
        return totalSalary;
    }
}
