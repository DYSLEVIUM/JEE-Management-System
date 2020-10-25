package sample;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import javax.swing.*;
import java.io.IOException;
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
    private Button minimizeBtn;

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
            String sid = rollOrIdBtn.getText();
            String pwd = passwordBtn.getText();

            String category;

            //  checking if user is admin
            if(sid.equals("admin") && pwd.equals("admin")){
                //closing current stage
                Stage stage = (Stage) closeBtn.getScene().getWindow();
                stage.close();

                //making another stage
                Stage adminStage = new Stage();
                adminStage.initStyle(StageStyle.UNDECORATED);

                Parent admin = FXMLLoader.load(getClass().getResource("admin.fxml"));

                Scene scene = new Scene(admin, Main.height , Main.width);

                scene.getStylesheets().add(getClass().getResource("../res/admin.css").toExternalForm());
                adminStage.setTitle("Candidate Details - Admin");

                scene.setFill(Color.TRANSPARENT);
                adminStage.initStyle(StageStyle.TRANSPARENT);

                Rectangle2D screenBounds = Screen.getPrimary().getBounds(); //  getting displayInfo

                //  setting position of window at center
                adminStage.setX((screenBounds.getMaxX()-Main.height)/2);
                adminStage.setY((screenBounds.getMaxY()-Main.width)/2);

                adminStage.setScene(scene);
                adminStage.show();
            }else{
                if(sid.charAt(sid.length()-1)=='G'){
                    category = "general";
                }else if(sid.charAt(sid.length()-1)=='R'){
                    category = "reservation";
                }else{
                    throw new Exception("Incorrect Roll Number!");
                }

                int id = 0;
                for(int i=0;i<sid.length()-1;++i){
                    if(sid.charAt(i)>='0'&& sid.charAt(i)<='9'){
                        id=id*10+(sid.charAt(i)-'0');
                    }else{
                        throw new Exception("Incorrect Roll Number!");
                    }
                }

                conn = databaseConnection.connect();
                stmt = conn.createStatement();

                String sql = "SELECT * FROM students WHERE students.rollnumber='"+id+"' AND students.password='"+pwd+"' AND students.category='"+category+"'";
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    //getting student info
                    sql = "SELECT * FROM students,marks WHERE students.rollnumber='"+id+"' AND students.password='"+pwd+"' AND students.category='"+category+"' AND students.rollnumber=marks.rollnumber";
                    rs = stmt.executeQuery(sql);

                    candidateController.tstudentName =  rs.getString("studentName").substring(0,1).toUpperCase()+rs.getString("studentName").substring(1);
                    candidateController.tstudentRoll =  sid;
                    candidateController.tstudentSex =  rs.getString("sex").substring(0,1).toUpperCase()+rs.getString("sex").substring(1);
                    candidateController.tstudentCategory =  rs.getString("category").substring(0,1).toUpperCase()+rs.getString("category").substring(1);
                    candidateController.tstudentFName =  rs.getString("fName").substring(0,1).toUpperCase()+rs.getString("fName").substring(1);
                    candidateController.tstudentMName =  rs.getString("mName").substring(0,1).toUpperCase()+rs.getString("mName").substring(1);
                    candidateController.tstudentmM =  rs.getInt("maths");
                    candidateController.tstudentpM =  rs.getInt("physics");
                    candidateController.tstudentcM =  rs.getInt("chemistry");

                    //closing current stage
                    Stage stage = (Stage) closeBtn.getScene().getWindow();
                    stage.close();

                    //making another stage
                    Stage candidateStage = new Stage();
                    candidateStage.initStyle(StageStyle.UNDECORATED);

                    Parent admin = FXMLLoader.load(getClass().getResource("candidate.fxml"));

                    Scene scene = new Scene(admin, Main.height , Main.width);

                    scene.getStylesheets().add(getClass().getResource("../res/candidate.css").toExternalForm());
                    candidateStage.setTitle("Candidate Details - Student");

                    scene.setFill(Color.TRANSPARENT);
                    candidateStage.initStyle(StageStyle.TRANSPARENT);

                    Rectangle2D screenBounds = Screen.getPrimary().getBounds(); //  getting displayInfo

                    //  setting position of window at center
                    candidateStage.setX((screenBounds.getMaxX()-Main.height)/2);
                    candidateStage.setY((screenBounds.getMaxY()-Main.width)/2);

                    candidateStage.setScene(scene);
                    candidateStage.show();

                    stmt.closeOnCompletion();
                }else
                    throw new Exception("Roll Number or Password is incorrect!");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
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

            conn = databaseConnection.connect();
            stmt = conn.createStatement();

            String sql;

            //  checking if the candidate already exists
            sql = "SELECT * FROM students WHERE students.studentName='"+studentName+"' AND students.password='"+password+"' AND students.category='"+category+"' AND students.fName='"+fName+"' AND students.mName='"+mName+"' AND students.dobD='"+dobDay+"' AND students.dobM='"+dobMonth+"' AND students.dobY='"+dobYear+"'";
            rs = stmt.executeQuery(sql);

            if(rs.next()){
                throw new Exception("Candidate Alredy exists!");
            }

            //  inserting into students database
            sql = "INSERT INTO students(password, studentName, fName, mName, sex, category, dobD, dobM, dobY) VALUES ('"+password+"','"+studentName+"', '"+fName+"','"+mName+"', '"+selectedSex+"', '"+category+"', '"+dobDay+"', '"+dobMonth+"', '"+dobYear+"')";
            stmt.executeUpdate(sql);


            //  getting the roll number from database
            sql = "SELECT * FROM students WHERE students.studentName='"+studentName+"' AND students.password='"+password+"' AND students.category='"+category+"' AND students.fName='"+fName+"' AND students.mName='"+mName+"' AND students.dobD='"+dobDay+"' AND students.dobM='"+dobMonth+"' AND students.dobY='"+dobYear+"'";
            rs = stmt.executeQuery(sql);

            int databaseRoll = rs.getInt("rollNumber");

            System.out.println(databaseRoll);

            //  inserting into marks database
            sql = "INSERT INTO marks VALUES ('"+databaseRoll+"','"+ 0 +"', '"+0+"','"+0+"')";
            stmt.executeUpdate(sql);

            StringBuilder genRoll = new StringBuilder();

            genRoll.append("0".repeat(Math.max(0, 6 - (String.valueOf(databaseRoll).length()+ 1))));   //appending required 0's to roll

            genRoll.append(databaseRoll);

            if(category.equals("general")){
                genRoll.append('G');
            }else{
                genRoll.append('R');
            }

            stmt.closeOnCompletion();

            JOptionPane.showMessageDialog(null,"Registered Successful! Your roll Number is "+ genRoll);
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
        Stage stage = (Stage) minimizeBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    public void meritListBtnClick(ActionEvent actionEvent) throws IOException {
        //closing current stage
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();

        //making another stage
        Stage meritListStage = new Stage();
        meritListStage.initStyle(StageStyle.UNDECORATED);

        Parent admin = FXMLLoader.load(getClass().getResource("meritList.fxml"));

        Scene scene = new Scene(admin, Main.height , Main.width);

        scene.getStylesheets().add(getClass().getResource("../res/meritList.css").toExternalForm());
        meritListStage.setTitle("Merit List Pane");

        scene.setFill(Color.TRANSPARENT);
        meritListStage.initStyle(StageStyle.TRANSPARENT);

        Rectangle2D screenBounds = Screen.getPrimary().getBounds(); //  getting displayInfo

        //  setting position of window at center
        meritListStage.setX((screenBounds.getMaxX()-Main.height)/2);
        meritListStage.setY((screenBounds.getMaxY()-Main.width)/2);

        meritListStage.setScene(scene);
        meritListStage.show();
    }
}
