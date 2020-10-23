package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane draggableArea;

    double xOffset=0;
    double yOffset=0;

    @FXML
    private Button closeBtn;

    @FXML
    private JFXTextField fullName;

    @FXML
    private JFXTextField fatherName;

    @FXML
    private  JFXTextField motherName;

    @FXML
    private JFXRadioButton loginBtn;

    @FXML
    private JFXRadioButton registerBtn;

    @FXML
    private JFXRadioButton generalBtn;

    @FXML
    private  JFXRadioButton reservationBtn;

    @FXML
    private JFXRadioButton maleBtn;

    @FXML
    private JFXRadioButton femaleBtn;

    @FXML
    private JFXTextField rollOrIdBtn;

    @FXML
    private JFXPasswordField passwordBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void loginBtnClick(){
        String id = rollOrIdBtn.getText();
        String pwd = passwordBtn.getText();

        System.out.println(id);
        System.out.println(pwd);
    }

    public void registerBtnClick(){
        String studentName = fullName.getText();
        String fName = fatherName.getText();
        String mName = motherName.getText();
        String selectedSex = maleBtn.isSelected()?"male":"female";
        String category = generalBtn.isSelected()?"general":"reservation";

        System.out.println(studentName);
        System.out.println(fName);
        System.out.println(mName);
        System.out.println(selectedSex);
        System.out.println(category);
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
