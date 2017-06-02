package Application.Tab.Employee;

import Application.Database.DatabaseUtil;
import Application.Listener.EmployeeListener;
import Application.Model.Employee;
import Application.Utility.MyAlert;
import Application.Utility.MyPreference;
import Application.Utility.MyUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sohel on 5/28/2017.
 */
public class AddEmployee implements Initializable {

    private static final String FOCUS_COLOR = "#0c94c9";

    private String filePath;
    private String fileName;
    private File imageDir;

    private EmployeeListener employeeListener;

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

    @FXML
    void selectPhotoClicked(ActionEvent event) {



        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(imageDir);
        //fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpeg"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            filePath = selectedFile.getAbsolutePath();
            fileName = selectedFile.getName();
            File imageFile = new File(filePath);
            Image image = new Image(imageFile.toURI().toString());
            employee_photo.setImage(image);
            System.out.println(filePath);
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Creating the Image Directory
        imageDir = new File(System.getProperty("user.home"),".RiceMill/EmployeeImage");
        if (! imageDir.exists()) {
            imageDir.mkdirs();
        }

        // Save Image Dir in Preference for Future Use
        MyPreference.getInstance().setImageFolder(imageDir.getAbsolutePath());

        filePath = "";

        employee_name.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0){
                employee_name.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                employee_name.setFocusColor(Color.RED);
            }
        });

        employee_fathers_name.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0){
                employee_fathers_name.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                employee_fathers_name.setFocusColor(Color.RED);
            }
        });

        employee_contact.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0){
                employee_contact.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                employee_contact.setFocusColor(Color.RED);
            }
        });

        employee_designation.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0){
                employee_designation.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                employee_designation.setFocusColor(Color.RED);
            }
        });

        employee_salary.textProperty().addListener((observable, oldValue, newValue) -> {
            if(MyUtil.isNumeric(newValue)){
                employee_salary.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                employee_salary.setFocusColor(Color.RED);
            }
        });

    }

    public void setEmployeeListener(EmployeeListener employeeListener){
        this.employeeListener = employeeListener;
    }

    @FXML
    void onSaveClicked(ActionEvent event) {
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

        Employee employee = new Employee(employeeName,employeeFathersName,employeeAddress,employeeContact,employeeEmail,employeeDesignation,fileName,Double.parseDouble(employeeSalStr));
        if(DatabaseUtil.getInstance().insertEmployee(employee)){
            Alert alert = MyAlert.successAlert("Employee Added Successfully");
            alert.showAndWait();
            if(employeeListener != null){
                employeeListener.addEmployee(employee);
            }
        }else{
            Alert alert = MyAlert.errorAlrt("EProblem in Added Employee");
            alert.showAndWait();
        }
    }

    @FXML
    void onCancelClicked(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }


}
