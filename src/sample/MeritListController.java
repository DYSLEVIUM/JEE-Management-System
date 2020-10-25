package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tableModels.adminTableModel;
import tableModels.meritListTableModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MeritListController implements Initializable {
    @FXML
    private AnchorPane draggableArea;

    double xOffset=0;
    double yOffset=0;

    @FXML
    private Button closeBtn;

    @FXML
    private Button minimizeBtn;

    @FXML
    private TableView<meritListTableModel> meritListTableView;

    @FXML
    public TableColumn<meritListTableModel,String> rank;

    @FXML
    public TableColumn<meritListTableModel,String> tableRoll;

    @FXML
    public TableColumn<meritListTableModel,String> tableName;

    @FXML
    public TableColumn<meritListTableModel,String> tableCategory;

    @FXML
    public TableColumn<meritListTableModel,String> totalMarks;

    @FXML
    public TableColumn<meritListTableModel,String> tableMaths;

    @FXML
    public TableColumn<meritListTableModel,String> tablePhysics;

    @FXML
    public TableColumn<meritListTableModel,String> tableChemistry;

    ObservableList<meritListTableModel> oblist = FXCollections.observableArrayList();

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

        try {
            this.populateTableFromDatabase();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public void populateTableFromDatabase() throws SQLException {
        Connection conn = databaseConnection.connect();
        Statement stmt = conn.createStatement();

        String sql = "SELECT students.rollnumber, students.studentName,students.category, marks.maths+marks.physics+marks.chemistry AS total, marks.maths, marks.physics, marks.chemistry FROM students, marks WHERE students.rollnumber=marks.rollnumber ORDER BY total DESC, marks.maths DESC, marks.physics DESC, marks.chemistry DESC";
        ResultSet rs = stmt.executeQuery(sql);

        int i=0;
        while(rs.next()){
            StringBuilder genRoll = new StringBuilder();

            genRoll.append("0".repeat(Math.max(0, 6 - (String.valueOf(rs.getInt("rollnumber")).length()+ 1))));   //appending required 0's to roll

            genRoll.append(rs.getInt("rollnumber"));

            if(rs.getString("category").equals("general")){
                genRoll.append('G');
            }else{
                genRoll.append('R');
            }

            oblist.add(new meritListTableModel(++i,genRoll.toString(),rs.getString("studentname"),rs.getString("category"),rs.getInt("total"),rs.getInt("maths"),rs.getInt("physics"),rs.getInt("chemistry")));
        }

        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableRoll.setCellValueFactory(new PropertyValueFactory<>("rollnumber"));
        tableName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        tableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        totalMarks.setCellValueFactory(new PropertyValueFactory<>("totalMarks"));
        tableMaths.setCellValueFactory(new PropertyValueFactory<>("maths"));
        tablePhysics.setCellValueFactory(new PropertyValueFactory<>("physics"));
        tableChemistry.setCellValueFactory(new PropertyValueFactory<>("chemistry"));

        meritListTableView.setItems(oblist);

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
