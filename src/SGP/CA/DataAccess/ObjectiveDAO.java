/**
 * @autor Hector David
 */
package SGP.CA.DataAccess;

import SGP.CA.Domain.Objective;
import SGP.CA.DataAccess.Interfaces.IObjectiveDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ObjectiveDAO implements IObjectiveDAO {

    /**
     *
     * @param objective manda un objeto del tipo Objective que contiene toda la informacion
     * que se desea guardar del objetivo
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException se cacha una SQLException en un caso de un posible error de conexion
     * a la base de datos.
     */

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

    /**
     *
     * @param objectiveTitle titulo del objetivo que desea buscar en la base de datos.
     * @return objective En caso de encontrar una coincidencia, se regresa un objeto
     * del tipo Objective con toda la informacion del objetivo coincidente.
     * @throws SQLException Se cacha una SQL Exception en caso de un posible error de conexion
     * a la base de datos
     */

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

    /**
     *
     * @param newObjective Manda un objeto del tipo Objective que contiene toda la informacion
     * que se desea modificar del objetivo.
     * @param oldObjectiveTitle Contiene el titulo del objetivo que se desea modificar
     * @return successfulUpdate Contiene el numero que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos
     */

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

    /**
     *
     * @param objectiveTitle Contiene el titulo del objetivo que se desea eliminar
     * @return successfulUpdate Contiene el numero que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos
     */

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

    /**
     *
     * @return allObjectives Lista con todos los objetivos encontrados en la base de datos.
     * @throws SQLException se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos.
     */

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
