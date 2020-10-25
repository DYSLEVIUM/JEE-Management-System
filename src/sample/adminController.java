package sample;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import tableModels.adminTableModel;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class adminController implements Initializable {

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
            Connection conn = databaseConnection.connect();
            Statement stmt = conn.createStatement();

            String sql = "SELECT students.rollnumber, students.studentName,students.category, marks.maths, marks.physics, marks.chemistry FROM students, marks WHERE students.rollnumber=marks.rollnumber";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                oblist.add(new adminTableModel(rs.getString("rollnumber"),rs.getString("studentName"),rs.getString("category"),rs.getInt("maths"),rs.getInt("physics"),rs.getInt("chemistry")));
            }

            tableRoll.setCellValueFactory(new PropertyValueFactory<>("rollnumber"));
            tableName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
            tableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            tableMaths.setCellValueFactory(new PropertyValueFactory<>("maths"));
            tablePhysics.setCellValueFactory(new PropertyValueFactory<>("physics"));
            tableChemistry.setCellValueFactory(new PropertyValueFactory<>("chemistry"));

            studentTableAdminView.setItems(oblist);

        }catch(Exception e){
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
}
