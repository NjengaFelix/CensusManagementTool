package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String url = "jdbc:mysql://localhost/census_management_tool", usrName = "root", password = "", driver = "com.mysql.jdbc.Driver";
    private static Connection con;

        public static Connection getConnection() {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, usrName, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return con;
        }

}
