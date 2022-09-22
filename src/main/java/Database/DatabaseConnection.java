package Database;

import Settings.PropertiesManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection db;
    String url = PropertiesManager.getInstance().getProperty("url");
    String user = PropertiesManager.getInstance().getProperty("user");
    String password = PropertiesManager.getInstance().getProperty("password");

    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            db = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }



}
