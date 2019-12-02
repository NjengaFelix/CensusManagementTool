package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String url = "jdbc:mysql://localhost:3306/census_management_tool", usrName = "root", password = "", driver = "com.mysql.jbdc.Driver";
    private static Connection con;

        public static Connection getConnection() {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, usrName, password);
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return con;
        }

}
