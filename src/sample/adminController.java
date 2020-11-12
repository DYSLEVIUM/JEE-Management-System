package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tableModels.adminTableModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class adminController implements Initializable {

    public Label studentName;
    public Label studentRoll;
    public Label studentCategory;
    public JFXTextField studentMp;
    public JFXTextField studentMm;
    public JFXTextField studentMc;

    @FXML
    private AnchorPane draggableArea;

    double xOffset=0;
    double yOffset=0;

    @FXML
    private Button closeBtn;

    @FXML
    private Button minimizeBtn;

    @FXML
    private TableView<adminTableModel> studentTableAdminView;

    @FXML
    public TableColumn<adminTableModel,String> tableRoll;

    @FXML
    public TableColumn<adminTableModel,String> tableName;

    @FXML
    public TableColumn<adminTableModel, String> tableCategory;

    @FXML
    public TableColumn<adminTableModel, Integer> tableMaths;

    @FXML
    public TableColumn<adminTableModel, Integer> tablePhysics;

    @FXML
    public TableColumn<adminTableModel, Integer> tableChemistry;

    ObservableList<adminTableModel> oblist = FXCollections.observableArrayList();

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

        try{
            this.populateTableFromDatabase();

            //  adding event listener to table to populate fields when selected
            studentTableAdminView.getSelectionModel().selectedItemProperty().addListener((observableValue, adminTableModel, t1) -> {
                if(studentTableAdminView.getSelectionModel().getSelectedItem()!=null){
                    TableView.TableViewSelectionModel selectionModel = studentTableAdminView.getSelectionModel();
                    adminTableModel selection = studentTableAdminView.getSelectionModel().getSelectedItem();
                    populateFields(selection);
                }
            });

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public void populateTableFromDatabase() throws SQLException {
        Connection conn = databaseConnection.connect();
        Statement stmt = conn.createStatement();

        String sql = "SELECT students.rollnumber, students.studentName,students.category, marks.maths, marks.physics, marks.chemistry FROM students, marks WHERE students.rollnumber=marks.rollnumber";
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            StringBuilder genRoll = new StringBuilder();

            genRoll.append("0".repeat(Math.max(0, 6 - (String.valueOf(rs.getInt("rollnumber")).length()+ 1))));   //appending required 0's to roll

            genRoll.append(rs.getInt("rollnumber"));

            if(rs.getString("category").equals("general")){
                genRoll.append('G');
            }else{
                genRoll.append('R');
            }

            oblist.add(new adminTableModel(genRoll.toString(),rs.getString("studentName").substring(0,1).toUpperCase()+rs.getString("studentName").substring(1),rs.getString("category").substring(0,1).toUpperCase()+rs.getString("category").substring(1),rs.getInt("maths"),rs.getInt("physics"),rs.getInt("chemistry")));
        }

        tableRoll.setCellValueFactory(new PropertyValueFactory<>("rollnumber"));
        tableName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        tableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tableMaths.setCellValueFactory(new PropertyValueFactory<>("maths"));
        tablePhysics.setCellValueFactory(new PropertyValueFactory<>("physics"));
        tableChemistry.setCellValueFactory(new PropertyValueFactory<>("chemistry"));

        studentTableAdminView.setItems(oblist);
        stmt.closeOnCompletion();
    }

    public void populateFields(adminTableModel mod){
        this.studentName.setText(mod.getStudentName().substring(0,1).toUpperCase()+mod.getStudentName().substring(1));
        this.studentRoll.setText(mod.getRollnumber());
        this.studentCategory.setText(mod.getCategory().substring(0,1).toUpperCase()+mod.getCategory().substring(1));
        this.studentMm.setText(String.valueOf(mod.getMaths()));
        this.studentMp.setText(String.valueOf(mod.getPhysics()));
        this.studentMc.setText(String.valueOf(mod.getChemistry()));
    }

    public void updateBtnClick() {
        try{
            String sid = this.studentRoll.getText();

            int id = 0;
            for(int i=0;i<sid.length()-1;++i){
                if(sid.charAt(i)>='0'&& sid.charAt(i)<='9'){
                    id=id*10+(sid.charAt(i)-'0');
                }else{
                    throw new Exception("Incorrect Roll Number!");
                }
            }

            Connection conn = databaseConnection.connect();
            Statement stmt = conn.createStatement();

            String sql = "UPDATE marks SET maths="+Integer.parseInt(this.studentMm.getText())+",physics="+Integer.parseInt(this.studentMp.getText())+",chemistry="+Integer.parseInt(this.studentMc.getText())+" WHERE rollnumber="+id+"";

            stmt.execute(sql);

            //  clearing table
            oblist.clear();

            //  populating table
            populateTableFromDatabase();

            JOptionPane.showMessageDialog(null,"Update Successful!");
            stmt.closeOnCompletion();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
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

    public void backBtnClick() throws IOException {
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
