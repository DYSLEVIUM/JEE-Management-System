package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    int height = 1000;
    int width = 600;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        primaryStage.initStyle(StageStyle.UNDECORATED); //  remove windows decoration

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Login | Register");
        primaryStage.setScene(new Scene(root, this.height, this.width));

        Rectangle2D screenBounds = Screen.getPrimary().getBounds(); //  getting displayInfo

        //  setting position of window at center
        primaryStage.setX((screenBounds.getMaxX()-this.height)/2);
        primaryStage.setY((screenBounds.getMaxY()-this.width)/2);

        primaryStage.setResizable(false);   //  setting resizable to false
        primaryStage.show();
    }

    public static void main(String[] args) {
        databaseConnection.connect();
        launch(args);
    }
}
