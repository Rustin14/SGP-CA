package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IPrototypeDAO;
import SGP.CA.Domain.Prototype;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrototypeDAO implements IPrototypeDAO {

    @Override
    public int savePrototype(Prototype prototype) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO prototype (prototypeName, idEvidence) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, prototype.getPrototypeName());
        statement.setInt(2, prototype.getIdEvidence());
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

    @Override
    public Prototype searchPrototypeByName(String prototypeName) throws SQLException {
        Prototype prototype = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM prototype WHERE prototypeName LIKE '%?%'";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, prototypeName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            prototype = new Prototype();
            prototype.setIdPrototype(resultSet.getInt("idPrototype"));
            prototype.setPrototypeName(resultSet.getString("prototypeName"));
            prototype.setIdEvidence(resultSet.getInt("idEvidence"));
        }
        return prototype;
    }

    @Override
    public ArrayList<Prototype> getAllPrototypes() throws SQLException {
        ArrayList<Prototype> allPrototypes = new ArrayList<>();
        Prototype prototype = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM prototype";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            prototype = new Prototype();
            prototype.setIdPrototype(resultSet.getInt("idPrototype"));
            prototype.setPrototypeName(resultSet.getString("prototypeName"));
            prototype.setIdEvidence(resultSet.getInt("idEvidence"));
            allPrototypes.add(prototype);
        }
        return allPrototypes;
    }
}
