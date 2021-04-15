package SGP.CA.DataAccess;

import SGP.CA.Domain.Objective;
import SGP.CA.DataAccess.Interfaces.IObjectiveDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectiveDAO implements IObjectiveDAO{

    @Override
    public int saveObjective(Objective objective) throws SQLException, ClassNotFoundException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO objective (description, strategy, title) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, objective.getDescription());
        statement.setString(2, objective.getStrategy());
        statement.setString(3, objective.getObjectiveTitle());
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    @Override
    public Objective searchObjectiveByTitle(String objectiveTitle) throws SQLException, ClassNotFoundException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM objective WHERE title = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, objectiveTitle);
        ResultSet results = preparedStatement.executeQuery();
        Objective objective = null;
        while (results.next()) {
            objective = new Objective();
            objective.setDescription(results.getString("description"));
            objective.setStrategy(results.getString("strategy"));
            objective.setObjectiveTitle(results.getString("title"));
        }
        dataBaseConnection.closeConnection();
        return objective;
    }

    @Override
    public int modifyObjective (Objective newObjective, String oldObjectiveTitle) throws SQLException, ClassNotFoundException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE objective set description = ?, strategy = ?, title = ? where title = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newObjective.getDescription());
        statement.setString(2, newObjective.getStrategy());
        statement.setString(3, newObjective.getObjectiveTitle());
        statement.setString(4, oldObjectiveTitle);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }
}
