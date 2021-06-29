/**
 * @autor Hector David
 */
package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IStrategyDAO;
import SGP.CA.Domain.Strategy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StrategyDAO implements IStrategyDAO {

    /**
     *
     * @param strategy manda un objeto del tipo Strategy que contiene toda la informacion
     * que se desea guardar de la estrategia.
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException se cacha una SQLException en un caso de un posible error de conexion
     * a la base de datos.
     */

    @Override
    public int saveStrategy(Strategy strategy) throws SQLException {
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

    /**
     *
     * @param strategyTitle titulo de la estrategia que desea buscar en la base de datos.
     * @return strategy En caso de encontrar una coincidencia, se regresa un objeto
     * del tipo Strategy con toda la informacion de la estrategia coincidente.
     * @throws SQLException Se cacha una SQL Exception en caso de un posible error de conexion
     * a la base de datos
     */

    @Override
    public Strategy searchStrategyByStrategy(String strategyTitle) throws SQLException {
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

    /**
     *
     * @param newStrategy Manda un objeto del tipo Strategy que contiene toda la informacion
     * que se desea modificar de la estrategia.
     * @param oldStrategy Contiene el titulo de la estrategia que se desea modificar
     * @return successfulUpdate Contiene el numero que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos
     */

    @Override
    public int modifyStrategy(Strategy newStrategy, String oldStrategy) throws SQLException {
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

    /**
     *
     * @param strategyTitle Contiene el titulo de la estrategia que se desea eliminar
     * @return successfulUpdate Contiene el numero que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos
     */

    @Override
    public int deleteStrategy(String strategyTitle) throws SQLException {
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM strategy WHERE strategy = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, strategyTitle);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    /**
     *
     * @return allStrategies Lista con todas las estrategias encontrados en la base de datos.
     * @throws SQLException se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos.
     */

    @Override
    public ArrayList<Strategy> getAllStrategy () throws SQLException {
        Strategy strategy = null;
        ArrayList<Strategy> allStrategies = new ArrayList<>();
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM strategy";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet results = preparedStatement.executeQuery();
        while (results.next()) {
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
