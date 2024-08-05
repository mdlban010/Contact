/*
 * A class that connects a database via JDBC
 * Banele Mdluli
 * 2024-07-27
 * You can edit the variables and put your own database information 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/contactdb";//this is my database name "contactdb"
    private static final String USER = "root";
    private static final String PASSWORD = "your_new_password";//if you want to check with your own database you can put your password here, this is my database pasword

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
