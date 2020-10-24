package sample;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    @FXML
    private AnchorPane draggableArea;

    double xOffset=0;
    double yOffset=0;

    @FXML
    private Button closeBtn;

    @FXML
    private JFXTextField candidateName;

    @FXML
    private JFXPasswordField regPassword;

    @FXML
    private JFXTextField fatherName;

    @FXML
    private  JFXTextField motherName;

    @FXML
    private JFXRadioButton generalBtn;

    @FXML
    private  JFXRadioButton reservationBtn;

    @FXML
    private JFXRadioButton maleBtn;

    @FXML
    private JFXRadioButton femaleBtn;

    @FXML
    private JFXComboBox yeardropdown;

    @FXML
    private JFXComboBox monthdropdown;

    @FXML
    private JFXComboBox daydropdown;

    @FXML
    private JFXButton registerBtn;

    @FXML
    private JFXTextField rollOrIdBtn;

    @FXML
    private JFXPasswordField passwordBtn;

    @FXML
    private JFXButton loginBtn;

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

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

        generalBtn.setSelectedColor(Color.valueOf("#3a92ef"));
        reservationBtn.setSelectedColor(Color.valueOf("#3a92ef"));

        maleBtn.setSelectedColor(Color.valueOf("#3a92ef"));
        femaleBtn.setSelectedColor(Color.valueOf("#3a92ef"));

        ObservableList<Integer> yearList = yeardropdown.getItems();
        ObservableList<Integer> dayList = daydropdown.getItems();
        ObservableList<String> monthList = monthdropdown.getItems();

        for(int i=2010;i>=1980;--i){
            yearList.add(i);
        }

        for(int i=1;i<=31;++i){
            dayList.add(i);
        }

        String[] months = {"January","February","March","April","May","June","July", "August", "September", "October", "November", "December"};

        monthList.addAll(Arrays.asList(months));
    }

    public void loginBtnClick(){
        try{
            String id = rollOrIdBtn.getText();
            String pwd = passwordBtn.getText();

            conn = databaseConnection.connect();
            stmt = conn.createStatement();

            String sql = "SELECT * FROM students WHERE students.rollnumber='"+id+"' AND students.password='"+pwd+"'";
            rs = stmt.executeQuery(sql);

            if(rs.next()){
                Stage candidateStage = new Stage();
                Scene scene = new Scene(new BorderPane(), 1000 , 600);

                candidateStage.setTitle("Candidate Details - Student");

                candidateStage.setScene(scene);
                candidateStage.show();
            }else{
                if(id.equals("2019UGCS097") && pwd.equals("qwaszx")){
                    Stage adminStage = new Stage();
                    Scene scene = new Scene(new BorderPane(), 1000 , 600);

                    adminStage.setTitle("Candidate Details - Admin");

                    adminStage.setScene(scene);
                    adminStage.show();
                }else
                    JOptionPane.showMessageDialog(null,"Roll Number or Password is incorrect!");
            }
            
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e+"");
        }

    }

    public void registerBtnClick(){
        try{
            String studentName = candidateName.getText();
            String password = regPassword.getText();
            String fName = fatherName.getText();
            String mName = motherName.getText();
            String selectedSex = maleBtn.isSelected()?"male":"female";
            String category = generalBtn.isSelected()?"general":"reservation";

            if(studentName.length()==0 && fName.length()==0 && mName.length()==0){
                throw new Exception("Please fill all the fields.");
            }

            int dobDay = (int) daydropdown.getValue();
            String dobMonth = (String) monthdropdown.getValue();
            int dobYear = (int) yeardropdown.getValue();

            Stage stage = new Stage();
            Scene scene = new Scene(new BorderPane(), 1000 , 600);
            stage.setTitle("Candidate Details");

            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
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
