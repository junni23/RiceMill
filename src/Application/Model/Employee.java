package Application.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Sohel on 5/28/2017.
 */
public class Employee {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty fathersName;
    private SimpleStringProperty address;
    private SimpleStringProperty contact;
    private SimpleStringProperty email;
    private SimpleStringProperty designation;
    private SimpleStringProperty picture;
    private SimpleDoubleProperty salary;


    public Employee(int id, String name, String fathersName, String address, String contact, String email, String designation, String picture, double salary) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.fathersName = new SimpleStringProperty(fathersName);
        this.address = new SimpleStringProperty(address);
        this.contact = new SimpleStringProperty(contact);
        this.email = new SimpleStringProperty(email);
        this.designation = new SimpleStringProperty(designation);
        this.picture = new SimpleStringProperty(picture);
        this.salary = new SimpleDoubleProperty(salary);

    }

    public Employee(String name, String fathersName, String address, String contact, String email, String designation, String picture, double salary) {
        this(-1,name,fathersName,address,contact,email,designation,picture,salary);

    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getFathersName() {
        return fathersName.get();
    }

    public SimpleStringProperty fathersNameProperty() {
        return fathersName;
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getContact() {
        return contact.get();
    }

    public SimpleStringProperty contactProperty() {
        return contact;
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getDesignation() {
        return designation.get();
    }

    public SimpleStringProperty designationProperty() {
        return designation;
    }

    public String getPicture() {
        return picture.get();
    }

    public SimpleStringProperty pictureProperty() {
        return picture;
    }

    public double getSalary() {
        return salary.get();
    }

    public SimpleDoubleProperty salaryProperty() {
        return salary;
    }

    @Override
    public String toString() {
        return getName();
    }
}
