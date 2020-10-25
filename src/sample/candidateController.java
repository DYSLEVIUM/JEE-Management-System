package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class candidateController implements Initializable {

    public Label studentName;
    public Label studentRoll;
    public Label studentSex;
    public Label studentCategory;
    public Label studentFName;
    public Label studentMName;
    public Label studentmM;
    public Label studentpM;
    public Label studentcM;

    public static String tstudentName;
    public static String tstudentRoll;
    public static String tstudentSex;
    public static String tstudentCategory;
    public static String tstudentFName;
    public static String tstudentMName;
    public static int tstudentmM;
    public static int tstudentpM;
    public static int tstudentcM;

    @FXML
    private AnchorPane draggableArea;

    double xOffset=0;
    double yOffset=0;

    @FXML
    private Button closeBtn;

    @FXML
    private Button minimizeBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //  making the custom title bar draggable
        draggableArea.setOnMousePressed(e->{
            Stage stage = (Stage) draggableArea.getScene().getWindow();

            xOffset = stage.getX() - e.getScreenX();
            yOffset = stage.getY() - e.getScreenY();
        });

        draggableArea.setOnMouseDragged(e->{
            Stage stage = (Stage) draggableArea.getScene().getWindow();

            stage.setX(e.getScreenX() + xOffset);
            stage.setY(e.getScreenY() + yOffset);
        });

        studentName.setText(tstudentName);
        studentRoll.setText(tstudentRoll);
        studentSex.setText(tstudentSex);
        studentCategory.setText(tstudentCategory);
        studentFName.setText(tstudentFName);
        studentMName.setText(tstudentMName);
        studentmM.setText(String.valueOf(tstudentmM));
        studentpM.setText(String.valueOf(tstudentpM));
        studentcM.setText(String.valueOf(tstudentcM));
    }

    public void closeBtnClick(){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void minimizeBtnClick(){
        Stage stage = (Stage) minimizeBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        Stage curStage = (Stage) closeBtn.getScene().getWindow();
        curStage.close();

        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED); //  remove windows decoration

        Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(login, Main.height, Main.width);
        scene.getStylesheets().add(getClass().getResource("../res/main.css").toExternalForm());   //  linking stylesheet
        stage.setTitle("JEE Management System");

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getBounds(); //  getting displayInfo

        //  setting position of window at center
        stage.setX((screenBounds.getMaxX()-Main.height)/2);
        stage.setY((screenBounds.getMaxY()-Main.width)/2);

        stage.setResizable(false);   //  setting resizable to false
        stage.show();
    }
}