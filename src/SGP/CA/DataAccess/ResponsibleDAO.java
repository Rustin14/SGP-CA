package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IResponsibleDAO;
import SGP.CA.Domain.Responsible;
import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResponsibleDAO implements IResponsibleDAO {

    @Override
    public int saveResponsible(int idMember) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO responsible (idMember) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idMember);
        int successfulUpdate = preparedStatement.executeUpdate();
        return successfulUpdate;
    }

}
