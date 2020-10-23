package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public AnchorPane draggableArea;
    double xOffset=0;
    double yOffset=0;

    public Line divider;
    public JFXButton loginBtn;
    public Button closeBtn;
    public Button minimizeBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //  -------------------------------- change position later of divider
        divider.setStartX(Main.height/2);
        divider.setStartY(100);
        divider.setEndX(Main.height/2);
        divider.setEndY(Main.width-100);

        //  making the custom title bar dragable
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
    }

    public void closeBtnClick(){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void minimizeBtnClick(){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.setIconified(true);
    }
}
