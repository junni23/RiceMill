package Application.Tab;

import Application.Database.DatabaseUtil;
import Application.Listener.EmployeeListener;
import Application.Listener.SalaryListener;
import Application.Model.Employee;
import Application.Model.Salary;
import Application.Tab.Employee.AddEmployee;
import Application.Tab.Employee.MonthSelector;
import Application.Tab.Employee.UpdateEmployee;
import Application.TableModel.SalaryData;
import Application.Utility.MyPreference;
import Application.Utility.MyUtil;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * Created by Sohel on 5/28/2017.
 */
public class EmployeeController implements Initializable,EmployeeListener,SalaryListener {

    private ObservableList<Employee> employeeObservableList;
    private Employee selectedEmployee;

    @FXML
    private AnchorPane employeeDetailPane;

    @FXML
    private JFXListView<Employee> employeeList;

    @FXML
    private ImageView employee_photo;

    @FXML
    private Text name;

    @FXML
    private Text fName;

    @FXML
    private Text address;

    @FXML
    private Text contact;

    @FXML
    private Text email;

    @FXML
    private Text designation;

    @FXML
    private Text Salary;

    @FXML
    private TableView<SalaryData> salary_table_view;
    private ObservableList<SalaryData> salaryDataObservableList;

    @FXML
    private TableColumn<SalaryData, String> col_date;

    @FXML
    private TableColumn<SalaryData, String> col_salary_month;

    @FXML
    private TableColumn<SalaryData, Double> col_salary;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSalaryTableColumn();
        employeeObservableList = FXCollections.observableArrayList(DatabaseUtil.getInstance().getAllEmployee());

        employeeList.getItems().setAll(employeeObservableList);

        employeeList.getSelectionModel().selectFirst();

        //System.out.println(DatabaseUtil.getInstance().getAllEmployee().size());

        employeeList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                selectedEmployee =employeeList.getSelectionModel().getSelectedItem();

                if(selectedEmployee!= null){
                    employeeDetailPane.setVisible(true);

                    name.setText(selectedEmployee.getName());
                    fName.setText(selectedEmployee.getFathersName());
                    address.setText(selectedEmployee.getAddress());
                    contact.setText(selectedEmployee.getContact());
                    email.setText(selectedEmployee.getEmail());
                    designation.setText(selectedEmployee.getDesignation());
                    Salary.setText(String.valueOf(selectedEmployee.getSalary()));

                    if(!(selectedEmployee.getPicture().isEmpty() || selectedEmployee.getPicture()==null) ){
                        String path = MyPreference.getInstance().getImageFolder()+"\\"+selectedEmployee.getPicture();
                        MyUtil.uploadImage(employee_photo,path);
                    }

                    // Update Table Data Here;
                    salaryDataObservableList = FXCollections.observableArrayList(DatabaseUtil.getInstance().getSalaryData(selectedEmployee.getId()));
                    salary_table_view.getItems().setAll(salaryDataObservableList);


                }

            }
        });

        if(selectedEmployee==null){
            employeeDetailPane.setVisible(false);
        }

    }



    @FXML
    void addEmployeeClicked(ActionEvent event) {
        loadWindow("Application/Tab/Employee/add_employee.fxml","Add New Employee");
    }

    @FXML
    void updateEmployee(ActionEvent event) {
        loadWindow("Application/Tab/Employee/update_employee.fxml","Update Employee");
    }

    private void loadWindow(String loc, String title){
        try {

            FXMLLoader loader =  new FXMLLoader(getClass().getClassLoader().getResource(loc));

            Parent parent = loader.load();

            if(loader.getController() instanceof AddEmployee){
                AddEmployee controller= (AddEmployee)loader.getController();
                controller.setEmployeeListener(this);
            }

            if(loader.getController() instanceof UpdateEmployee){
                UpdateEmployee controller= (UpdateEmployee)loader.getController();
                controller.setEmployeeListener(this);
                controller.setEmployee(selectedEmployee);
            }

            if(loader.getController() instanceof MonthSelector){
                MonthSelector controller= (MonthSelector)loader.getController();
                controller.setSalary(new Salary(selectedEmployee));
                controller.setSalaryListener(this);
            }







            //Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("sample/member/add_member.fxml"));

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUpdateEmployee(String loc, String title){
        try {

            FXMLLoader loader =  new FXMLLoader(getClass().getClassLoader().getResource(loc));

            Parent parent = loader.load();

            if(loader.getController() instanceof AddEmployee){
                AddEmployee controller= (AddEmployee)loader.getController();
                controller.setEmployeeListener(this);
            }



            //Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("sample/member/add_member.fxml"));



            if (parent== null){
                System.out.println("Parent Null");
            }
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeList.getItems().add(employee);
        employeeList.getItems().sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    @Override
    public void updateEmployee(Employee old, Employee current) {
        employeeList.getItems().remove(old);
        employeeList.getItems().add(current);

        employeeList.getItems().sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        updateEmployeeInfo(current);
        selectedEmployee = current;
        employeeList.getSelectionModel().select(selectedEmployee);
    }

    @FXML
    void paidSalary(ActionEvent event) {
        loadWindow("Application/Tab/Employee/month_selector.fxml","Salary");
    }

    private void updateEmployeeInfo(Employee employee) {
        name.setText(employee.getName());
        fName.setText(employee.getFathersName());
        address.setText(employee.getAddress());
        contact.setText(employee.getContact());
        email.setText(employee.getEmail());
        designation.setText(employee.getDesignation());
        Salary.setText(String.valueOf(employee.getSalary()));

        if(!(employee.getPicture().isEmpty() || employee.getPicture()==null) ){
            String path = MyPreference.getInstance().getImageFolder()+"\\"+employee.getPicture();
            MyUtil.uploadImage(employee_photo,path);
        }
    }

    private void initSalaryTableColumn() {

        col_date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SalaryData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SalaryData, String> param) {
                return param.getValue().dateProperty();
            }
        });

        col_salary_month.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SalaryData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SalaryData, String> param) {
                return param.getValue().paid_month_yearProperty();
            }
        });

        col_salary.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SalaryData, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<SalaryData, Double> param) {
                return param.getValue().amountProperty().asObject();
            }
        });

    }

    @Override
    public void updateSalary(SalaryData salaryData) {
        salary_table_view.getItems().add(salaryData);
    }
}
