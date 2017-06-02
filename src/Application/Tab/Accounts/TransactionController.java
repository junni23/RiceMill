package Application.Tab.Accounts;

import Application.Database.DatabaseUtil;
import Application.Model.Account;
import Application.Model.Transaction;
import Application.Utility.MyAlert;
import Application.Utility.MyUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sohel on 6/2/2017.
 */
public class TransactionController implements Initializable {
    private static final String FOCUS_COLOR = "#0c94c9";

    private ObservableList<Account> accountList;

    private SimpleStringProperty lastInvoiceNumber;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField txt_inv_no;

    @FXML
    private JFXTextField txt_desc;

    @FXML
    private JFXTextField txt_from;

    @FXML
    private JFXTextField txt_to;

    @FXML
    private JFXTextField txt_amount;

    @FXML
    private Text last_invoice;

    @FXML
    void cancelClicked(ActionEvent event) {
        MyUtil.closeWindow(rootPane);

    }

    @FXML
    void saveClicked(ActionEvent event) {

        String invoiceNo = txt_inv_no.getText().trim();
        String desc = txt_desc.getText().trim();
        String from = txt_from.getText().trim();
        String to = txt_to.getText().trim();
        String amount = txt_amount.getText().trim();

        if(invoiceNo.isEmpty()){
            txt_inv_no.requestFocus();
            txt_inv_no.setFocusColor(Color.RED);
            return;
        }
        if(desc.isEmpty()){
            txt_desc.requestFocus();
            txt_desc.setFocusColor(Color.RED);
            return;
        }
        if(from.isEmpty()){
            txt_from.requestFocus();
            txt_from.setFocusColor(Color.RED);
            return;
        }

        if(!validAccount(from)){
            txt_from.requestFocus();
            txt_from.setFocusColor(Color.RED);
            return;
        }
        if(to.isEmpty()){
            txt_to.requestFocus();
            txt_to.setFocusColor(Color.RED);
            return;
        }

        if(!validAccount(to)){
            txt_to.requestFocus();
            txt_to.setFocusColor(Color.RED);
            return;
        }

        if(to.equals(from)){
            txt_to.requestFocus();
            txt_to.setFocusColor(Color.RED);
            return;
        }

        if(amount.isEmpty()){
            txt_amount.requestFocus();
            txt_amount.setFocusColor(Color.RED);
            return;
        }

        if(!MyUtil.isNumeric(amount)){
            txt_amount.requestFocus();
            txt_amount.setFocusColor(Color.RED);
            return;
        }

        Account toAcc = getAccount(to);
        Account fromAcc = getAccount(from);

        Transaction transaction = new Transaction(invoiceNo,desc,fromAcc,toAcc,Double.parseDouble(amount));

        if(DatabaseUtil.getInstance().insertTransaction(transaction)){
            Alert alert = MyAlert.successAlert("Transaction Added Successfully");
            alert.showAndWait();
            MyUtil.closeWindow(rootPane);
        }else{
            Alert alert = MyAlert.errorAlrt("Problem in Adding Transaction");
            alert.showAndWait();
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lastInvoiceNumber = new SimpleStringProperty();
        lastInvoiceNumber.addListener((observable, oldValue, newValue) -> {
            last_invoice.setText(newValue);
        });
        lastInvoiceNumber.setValue(DatabaseUtil.getInstance().getLastTransactionInvoice());

        accountList = FXCollections.observableArrayList(DatabaseUtil.getInstance().getAllAccounts());
        TextFields.bindAutoCompletion(txt_from,accountList);
        TextFields.bindAutoCompletion(txt_to,accountList);

        txt_inv_no.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0){
                txt_inv_no.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                txt_inv_no.setFocusColor(Color.RED);
            }
        });

        txt_desc.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0){
                txt_desc.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                txt_desc.setFocusColor(Color.RED);
            }
        });

        txt_from.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0 && validAccount(newValue)){
                txt_from.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                txt_from.setFocusColor(Color.RED);
            }
        });

        txt_to.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0 && validAccount(newValue)){
                txt_to.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                txt_to.setFocusColor(Color.RED);
            }
        });

        txt_amount.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>0 && MyUtil.isNumeric(newValue)){
                txt_amount.setFocusColor(Paint.valueOf(FOCUS_COLOR));
            }else{
                txt_amount.setFocusColor(Color.RED);
            }
        });

    }


    private boolean validAccount(String acc){
        boolean retBool = false;

        for(Account x: accountList){
            if(x.getAccountName().equals(acc)){
                retBool = true;
                break;
            }
        }

        return retBool;
    }

    private Account getAccount(String accName){
        Account account=null;

        for(Account x: accountList){
            if(x.getAccountName().equals(accName)){
                account=x;
            }
        }

        return account;
    }
}
