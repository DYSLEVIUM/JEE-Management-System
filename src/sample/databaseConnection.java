package sample;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
    final static String JDBC_DRIVER = "org.sqlite.JDBC";
    final static String JDBC_URL = "jdbc:sqlite:D:/dev/java/JEE-Management-System/src/res/db";

    public static Connection connect(){
        Connection conn = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL);    //  connecting to database
            System.out.println("Connected to sqlite database!");
        }catch (ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,e+"");
            System.out.println(e+"");
        }
        return conn;
    }
}


