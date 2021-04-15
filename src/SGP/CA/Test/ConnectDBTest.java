package SGP.CA.Test;

import SGP.CA.DataAccess.ConnectDB;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectDBTest {

    ConnectDB connectDB = new ConnectDB();

    @Test
    public void connectDBtest() throws SQLException, ClassNotFoundException{
        Connection connection = connectDB.getConnection();
        Assert.assertNotNull(connection);
    }
}
