package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IStrategyDAO;
import SGP.CA.Domain.Strategy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StrategyDAO implements IStrategyDAO {

    @Override
    public int saveStrategy(Strategy strategy) throws SQLException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO strategy (action, goal, number, result, strategy) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, strategy.getAction());
        statement.setString(2, strategy.getGoal());
        statement.setInt(3, strategy.getNumber());
        statement.setString(4, strategy.getResult());
        statement.setString(5, strategy.getStrategy());
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    @Override
    public Strategy searchStrategyByStrategy(String strategyTitle) throws SQLException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM strategy WHERE strategy = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, strategyTitle);
        ResultSet results = preparedStatement.executeQuery();
        Strategy strategy = null;
        while (results.next()) {
            strategy = new Strategy();
            strategy.setAction(results.getString("action"));
            strategy.setGoal(results.getString("goal"));
            strategy.setNumber(results.getInt("number"));
            strategy.setResult(results.getString("result"));
            strategy.setStrategy(results.getString("strategy"));
        }
        dataBaseConnection.closeConnection();
        return strategy;
    }

    @Override
    public int modifyStrategy(Strategy newStrategy, String oldStrategy) throws SQLException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE strategy set action = ?, goal = ?, number = ?, result = ?, strategy = ? where strategy = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newStrategy.getAction());
        statement.setString(2, newStrategy.getGoal());
        statement.setInt(3, newStrategy.getNumber());
        statement.setString(4, newStrategy.getResult());
        statement.setString(5, newStrategy.getStrategy());
        statement.setString(6, oldStrategy);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    @Override
    public int deleteStrategy(String strategyTitle) throws SQLException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM strategy WHERE strategy = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, strategyTitle);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    @Override
    public ArrayList<Strategy> getAllStrategy () throws SQLException{
        Strategy strategy = null;
        ArrayList<Strategy> allStrategies = new ArrayList<>();
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM strategy";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet results = preparedStatement.executeQuery();
        while (results.next()){
            strategy = new Strategy();
            strategy.setStrategy(results.getString("strategy"));
            strategy.setNumber(results.getInt("number"));
            strategy.setGoal(results.getString("goal"));
            strategy.setAction(results.getString("action"));
            strategy.setResult(results.getString("result"));
            allStrategies.add(strategy);
        }
        return allStrategies;
    }
}
