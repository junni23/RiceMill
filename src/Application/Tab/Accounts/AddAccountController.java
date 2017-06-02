package Application.Tab.Accounts;

import Application.Database.DatabaseUtil;
import Application.Model.Account;
import Application.Utility.MyAlert;
import Application.Utility.MyUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Sohel on 5/30/2017.
 */
public class AddAccountController implements Initializable {
    private static final String FOCUS_COLOR = "#0c94c9";
    private ToggleGroup group;
    private List<String> accList = new ArrayList<>(Arrays.asList("Real","Personal","Nominal","Bank A/c"));
    private DatabaseUtil databaseUtil;

    private int isBankAccount;

    private ObservableList<String> accTypes = FXCollections.observableArrayList(accList);
    @FXML
    private AnchorPane rooPane;

    @FXML
    private VBox vbox;

    @FXML
    private JFXTextField account_name;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXComboBox<String> account_type;

    @FXML
    private JFXRadioButton radioYes;

    @FXML
    private JFXRadioButton radioNo;

    @FXML
    private AnchorPane radSection;

    @FXML
    void addAccount(ActionEvent event) {

        String name = account_name.getText().toString().trim();
        String address_txt = address.getText().toString().trim();
        String contact_txt = contact.getText().toString().trim();
        String email_txt = email.getText().toString().trim();
        String accType_txt = account_type.getValue();



        // Name and Account Type is mendatory

        if(name.isEmpty()){
            account_name.requestFocus();
            account_name.setFocusColor(Color.RED);
            return;
        }

        if(accType_txt==null){
            account_type.requestFocus();
            account_type.setFocusColor(Color.RED);
            return;
        }

        int accType = accList.indexOf(accType_txt);

        Account account = new Account(name,address_txt,contact_txt,email_txt,accType,-1);

        if(DatabaseUtil.getInstance().insertAccount(account)){
            Alert alert = MyAlert.successAlert("Account Added Successfully");
            alert.showAndWait();
            MyUtil.closeWindow(rooPane);
        }else {
            Alert alert = MyAlert.errorAlrt("Problem in Added Account");
            alert.showAndWait();
        }






    }

    @FXML
    void cancelOperation(ActionEvent event) {

        MyUtil.closeWindow(rooPane);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        group = new ToggleGroup();
        radioYes.setToggleGroup(group);
        radioNo.setToggleGroup(group);

        radSection.setVisible(false);

        account_type.setItems(accTypes);
        JFXDepthManager.setDepth(vbox,1);
        databaseUtil = DatabaseUtil.getInstance();

        /*account_type.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if((int) newValue==2){
                radSection.setVisible(true);
            }else {
                isBankAccount =-1;
            }
        });*/

        System.out.println(radioYes.isSelected());

        account_name.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0){
                account_name.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                account_name.setFocusColor(Color.RED);
            }
        });
    }
}
