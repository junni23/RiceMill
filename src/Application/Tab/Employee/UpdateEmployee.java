package Application.Tab.Employee;

import Application.Database.DatabaseUtil;
import Application.Listener.EmployeeListener;
import Application.Model.Employee;
import Application.Utility.MyAlert;
import Application.Utility.MyPreference;
import Application.Utility.MyUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sohel on 5/29/2017.
 */
public class UpdateEmployee implements Initializable {
    private Employee currentEmployee;
    private EmployeeListener listener;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView employee_photo;

    @FXML
    private JFXTextField employee_name;

    @FXML
    private JFXTextField employee_fathers_name;

    @FXML
    private JFXTextField employee_address;

    @FXML
    private JFXTextField employee_contact;

    @FXML
    private JFXTextField employee_email;

    @FXML
    private JFXTextField employee_designation;

    @FXML
    private JFXTextField employee_salary;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onCancelClicked(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onUpdateClicked(ActionEvent event) {
        String employeeName = employee_name.getText().toString().trim();
        String employeeFathersName = employee_fathers_name.getText().toString().trim();
        String employeeAddress = employee_address.getText().toString().trim();
        String employeeContact = employee_contact.getText().toString().trim();
        String employeeEmail = employee_email.getText().toString().trim();
        String employeeDesignation = employee_designation.getText().toString().trim();
        String employeeSalStr = employee_salary.getText().toString().trim();

        if(employeeName.isEmpty()){
            employee_name.requestFocus();
            employee_name.setFocusColor(Color.RED);
            return;
        }

        if(employeeFathersName.isEmpty()){
            employee_fathers_name.requestFocus();
            employee_fathers_name.setFocusColor(Color.RED);
            return;
        }

        if(employeeContact.isEmpty()){
            employee_contact.requestFocus();
            employee_contact.setFocusColor(Color.RED);
            return;
        }

        if(employeeDesignation.isEmpty()){
            employee_designation.requestFocus();
            employee_designation.setFocusColor(Color.RED);
            return;
        }


        if(!MyUtil.isNumeric(employeeSalStr)){
            employee_salary.requestFocus();
            employee_salary.setFocusColor(Color.RED);
            return;
        }

        Employee employee = new Employee(currentEmployee.getId(),employeeName,employeeFathersName,employeeAddress,employeeContact,employeeEmail,employeeDesignation,currentEmployee.getPicture(),Double.parseDouble(employeeSalStr));

        if(DatabaseUtil.getInstance().updateEmployee(employee)){
            Alert alert = MyAlert.successAlert("Employee Updated Successfully");
            alert.showAndWait();
            if(listener != null){
                listener.updateEmployee(currentEmployee,employee);
            }
            // Close Stage
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = MyAlert.errorAlrt("Problem in Added Employee");
            alert.showAndWait();
        }



    }

    public void setEmployee(Employee employee){
        this.currentEmployee = employee;
        employee_name.setText(employee.getName());
        employee_fathers_name.setText(employee.getFathersName());
        employee_address.setText(employee.getAddress());
        employee_contact.setText(employee.getContact());
        employee_email.setText(employee.getEmail());
        employee_designation.setText(employee.getDesignation());
        employee_salary.setText(String.valueOf(employee.getSalary()));

        if(!(employee.getPicture().isEmpty() || employee.getPicture()==null) ){
            String path = MyPreference.getInstance().getImageFolder()+"\\"+employee.getPicture();
            MyUtil.uploadImage(employee_photo,path);
        }
    }

    public void setEmployeeListener(EmployeeListener listener){
        this.listener = listener;
    }
}
