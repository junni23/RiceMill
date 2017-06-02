package Application.Utility;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Sohel on 5/15/2017.
 */
public class MyUtil {

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static void uploadImage(ImageView imageView, String path){
        File imageFile = new File(path);
        Image image = new Image(imageFile.toURI().toString());
        imageView.setImage(image);
    }

    public static void closeWindow(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
