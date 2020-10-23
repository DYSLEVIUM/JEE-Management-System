package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Line divider;
    public Button loginBtn;
    public Button closeBtn;
    public Button minimizeBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //  change position later of divider
        divider.setStartX(Main.width);
        divider.setStartY(0);
        divider.setEndX(Main.width);
        divider.setEndY(Main.height);
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
