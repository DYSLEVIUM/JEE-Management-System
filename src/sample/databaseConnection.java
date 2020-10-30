package sample;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
    private static File dbfile = new File("src\\res\\db");

    final static String JDBC_DRIVER = "org.sqlite.JDBC";
    final static String JDBC_URL = "jdbc:sqlite:" + dbfile.getAbsolutePath();

    public static Connection connect(){
        Connection conn = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL);    //  connecting to database
        }catch (ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return conn;
    }
}