package Application.Utility;

import javafx.scene.control.Alert;

/**
 * Created by Sohel on 5/17/2017.
 */
public class MyAlert {

    public static Alert successAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }

    public static Alert errorAlrt(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert;
    }
}
