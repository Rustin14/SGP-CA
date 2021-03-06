package SGP.CA.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    private static String url = "jdbc:mysql://tommy.heliohost.org:3306/rustin14_SGP-CA?serverTimezone=US/Central";
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String username = "rustin14";
    private static String password = "Flipper10011";
    private static Connection connection;

    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            if(!connection.isClosed()) {
                connection.close();
            }
        }
    }

}
