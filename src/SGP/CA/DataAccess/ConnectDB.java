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

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        connection = DriverManager.getConnection(url, username, password);
        System.out.println("Conexión exitosa.");
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            if(!connection.isClosed()) {
                connection.close();
            }
        }
    }

}
