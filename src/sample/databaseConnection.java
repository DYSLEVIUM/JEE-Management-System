package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
    public static Connection connect(){
        Connection con = null;
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:D:/dev/java/JEESystem/src/res/db");    //  connecting to database
            System.out.println("Connected to sqlite database!");
        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e+"");
        }
        return con;
    }
}
