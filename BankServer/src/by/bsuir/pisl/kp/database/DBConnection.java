package by.bsuir.pisl.kp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by alexk on 05.12.2016.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/autohouse";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection con;
    private static final DBConnection dbConnection = new DBConnection();
    private DBConnection() {}

    public static Connection getConnection() {
        if (dbConnection.con == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                dbConnection.con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return dbConnection.con;
    }

    public static void disconnect() {
        if(dbConnection.con != null) {
            try {
                dbConnection.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
