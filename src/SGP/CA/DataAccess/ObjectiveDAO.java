package SGP.CA.DataAccess;

import SGP.CA.Domain.Objective;
import SGP.CA.DataAccess.Interfaces.IObjectiveDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ObjectiveDAO implements IObjectiveDAO {

    @Override
    public int saveObjective(Objective objective) throws SQLException {
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
    public Objective searchObjectiveByTitle(String objectiveTitle) throws SQLException {
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
    public int modifyObjective (Objective newObjective, String oldObjectiveTitle) throws SQLException {
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

    @Override
    public int deleteObjective (String objectiveTitle) throws SQLException {
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM objective WHERE title = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, objectiveTitle);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    @Override
    public ArrayList<Objective> getAllObjectives () throws SQLException {
        Objective objective = null;
        ArrayList<Objective> allObjectives = new ArrayList<>();
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM objective";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet results = preparedStatement.executeQuery();
        while (results.next()) {
            objective = new Objective();
            objective.setObjectiveTitle(results.getString("title"));
            objective.setDescription(results.getString("description"));
            objective.setStrategy(results.getString("strategy"));
            allObjectives.add(objective);
        }
        return allObjectives;
    }
}
