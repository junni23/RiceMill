package Application.Tab;

import Application.Tab.Employee.AddEmployee;
import Application.Tab.Employee.UpdateEmployee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sohel on 5/30/2017.
 */
public class AccountTab implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void addAccount(ActionEvent event) {
        loadWindow("Application/Tab/Accounts/add_account.fxml","Add Account");
    }

    @FXML
    void addTransaction(ActionEvent event) {
        loadWindow("Application/Tab/Accounts/transaction.fxml","TransactionController");

    }

    private void loadWindow(String loc, String title){
        try {

            FXMLLoader loader =  new FXMLLoader(getClass().getClassLoader().getResource(loc));

            Parent parent = loader.load();

           /* if(loader.getController() instanceof AddEmployee){
                AddEmployee controller= (AddEmployee)loader.getController();
                controller.setEmployeeListener(this);
            }

            if(loader.getController() instanceof UpdateEmployee){
                UpdateEmployee controller= (UpdateEmployee)loader.getController();
                controller.setEmployeeListener(this);
                controller.setEmployee(selectedEmployee);
            }*/


            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
