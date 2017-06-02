package Application.Tab.Employee;

import Application.Database.DatabaseUtil;
import Application.Listener.SalaryListener;
import Application.Model.Account;
import Application.Model.Salary;
import Application.TableModel.SalaryData;
import Application.Utility.MyAlert;
import Application.Utility.MyUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sohel on 5/30/2017.
 */
public class MonthSelector implements Initializable {
    private SalaryListener listener;
    private static final String FOCUS_COLOR = "#0c94c9";
    private List<String> monthList = new ArrayList<>(Arrays.asList("Jan","Feb","Mar","Apr","May",
            "Jun","Jul","Aug","Sep","Oct","Nov","Dec"));
    private List<Integer> yearList = new ArrayList<>(Arrays.asList(2015,2016,2017,2018,2019,2020
        ,2021,2022,2023,2024,2025));

    private ObservableList<String> monthObservableList;
    private ObservableList<Integer> yearObservableList;

    SimpleStringProperty test;

    private ObservableList<String> paymentMethods = FXCollections.observableArrayList(Arrays.asList("Cash","Cheque"));

    private Salary salary;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField invoice_no;

    @FXML
    private JFXTextField month;

    @FXML
    private JFXTextField year;

    @FXML
    private JFXTextField paymentMethod;

    @FXML
    private GridPane bankInfo;

    @FXML
    private JFXComboBox<Account> bankAccounts;

    @FXML
    private Text lastInvoice;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        monthObservableList = FXCollections.observableArrayList(monthList);
        yearObservableList = FXCollections.observableArrayList(yearList);

        bankAccounts.getItems().setAll(FXCollections.observableArrayList(DatabaseUtil.getInstance().getAllBankAccounts()));

        TextFields.bindAutoCompletion(month,monthObservableList);
        TextFields.bindAutoCompletion(year,yearObservableList);
        TextFields.bindAutoCompletion(paymentMethod,paymentMethods);

        test = new SimpleStringProperty();
        test.addListener((observable, oldValue, newValue) -> {
            //System.out.println(oldValue);
            System.out.println(newValue);
        });
        test.setValue("sohel");

        lastInvoice.setText(DatabaseUtil.getInstance().getLastTransactionInvoice());

        // Invisible Bank Info
        bankInfo.setVisible(false);

        paymentMethod.textProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue.equals("Cheque")){
                bankInfo.setVisible(true);
            }else {
                bankInfo.setVisible(false);
            }
           // System.out.println(newValue);
        });


        invoice_no.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0){
                invoice_no.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                invoice_no.setFocusColor(Color.RED);
            }
        });


        month.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0 && isValidMonth(newValue)){
                month.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                month.setFocusColor(Color.RED);
            }
        });

        year.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0  && MyUtil.isNumeric(newValue)){
                if(isValidYear(Integer.parseInt(newValue))){
                    year.setFocusColor(Paint.valueOf(FOCUS_COLOR));
                }

            }else{
                year.setFocusColor(Color.RED);
            }
        });



    }

    public void setSalary(Salary salary){
        this.salary = salary;
    }

    public void setSalaryListener(SalaryListener listener){
        this.listener = listener;
    }

    private boolean isValidMonth(String month){
        boolean retBool = false;

        for (String x: monthList){
            if(x.equals(month)){
                retBool = true;
                break;
            }
        }

        return retBool;
    }

    private boolean isValidYear(int year){
        boolean retBool = false;

        for (int x: yearList){
            if(x==year){
                retBool = true;
                break;
            }
        }

        return retBool;
    }


    @FXML
    void cancelClicked(ActionEvent event) {
        MyUtil.closeWindow(rootPane);
    }

    @FXML
    void okClicked(ActionEvent event) {
        String monthTxt = month.getText().trim();
        String invoice_txt = invoice_no.getText().trim();
        String yearTxt = year.getText().toString().trim();

        if(invoice_txt.isEmpty()){
            invoice_no.requestFocus();
            invoice_no.setFocusColor(Color.RED);
            return;
        }

        if(monthTxt.isEmpty()){
            month.requestFocus();
            month.setFocusColor(Color.RED);
            return;
        }

        if(!isValidMonth(monthTxt)){
            month.requestFocus();
            month.setFocusColor(Color.RED);
            return;
        }

        if(yearTxt.isEmpty()){
            year.requestFocus();
            year.setFocusColor(Color.RED);
            return;
        }

        if(!MyUtil.isNumeric(yearTxt)){
            year.requestFocus();
            year.setFocusColor(Color.RED);
            return;
        }else{
            if(! isValidYear(Integer.parseInt(yearTxt))){
                year.requestFocus();
                year.setFocusColor(Color.RED);
                return;
            }
        }

        salary.setMonth_year(monthTxt+"-"+yearTxt);
        salary.setInvoice_number(invoice_txt);

        Account account = null;

        if(!bankInfo.isVisible()){
            System.out.println("YES");
            account = DatabaseUtil.getInstance().getAccountByName("Cash");
        }else{
            account = bankAccounts.getValue();
        }

        salary.setAccount(account);

        if(DatabaseUtil.getInstance().transactEmployeeSalary(salary)){
            Alert alert = MyAlert.successAlert("Salary transact Successfully");
            alert.showAndWait();
            if(listener!= null){
                DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SalaryData data = new SalaryData(dateFormat.format(new Date()),salary.getEmployee().getId(),salary.getMonth_year(),salary.getEmployee().getSalary());
                listener.updateSalary(data);
            }
            MyUtil.closeWindow(rootPane);
        }else {
            Alert alert = MyAlert.errorAlrt("Problem in Transact Data");
            alert.showAndWait();
        }





    }


}
