package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
}