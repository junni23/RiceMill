package Application;

import Application.Database.DatabaseHandler;
import Application.Database.DatabaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Linear Tech");
        primaryStage.setScene(new Scene(root, 1000, 680));
        primaryStage.resizableProperty().setValue(false);
        //primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseHandler.getInstance();
            }
        }).start();
    }

    @Override
    public void stop() throws Exception {
        DatabaseHandler.getInstance().closeConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
