package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IWorkPlanDAO;
import java.util.ArrayList;
import java.util.Date;
import SGP.CA.Domain.WorkPlan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkPlanDAO implements IWorkPlanDAO {

    /**
     *
     * @param workPlan manda un objeto del tipo WorkPlan que contiene toda la informacion
     * que se desea guardar del plan de trabajo.
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException se cacha una SQLException en un caso de un posible error de conexion
     * a la base de datos.
     */

    @Override
    public int saveWorkPlan(WorkPlan workPlan) throws SQLException {
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO workplan (endingDate, objective, startDate, workPlanKey) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        java.sql.Date endingDate = new java.sql.Date(workPlan.getEndingDate().getTime());
        statement.setDate(1, endingDate);
        statement.setString(2, workPlan.getObjective());
        java.sql.Date starDate = new java.sql.Date(workPlan.getStartDate().getTime());
        statement.setDate(3, starDate);
        statement.setString(4, workPlan.getWorkPlanKey());
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    /**
     *
     * @param workPlanKey clave del plan de trabajo que desea buscar en la base de datos.
     * @return workPlan En caso de encontrar una coincidencia, se regresa un objeto
     * del tipo WorkPlan con toda la informacion de la estrategia coincidente.
     * @throws SQLException Se cacha una SQL Exception en caso de un posible error de conexion
     * a la base de datos
     */

    @Override
    public WorkPlan searchWorkPlanByWorkPlanKey (String workPlanKey) throws SQLException {
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM workplan WHERE workPlanKey = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, workPlanKey);
        ResultSet results = preparedStatement.executeQuery();
        WorkPlan workplan = null;
        while (results.next()) {
            workplan = new WorkPlan();
            Date endingDate = new Date(results.getDate("endingDate").getTime());
            workplan.setEndingDate(endingDate);
            workplan.setObjective(results.getString("objective"));
            Date startDate = new Date(results.getDate("startDate").getTime());
            workplan.setStartDate(startDate);
            workplan.setWorkPlanKey(results.getString("workPlanKey"));
        }
        dataBaseConnection.closeConnection();
        return workplan;
    }

    /**
     *
     * @param newWorkPlan Manda un objeto del tipo WorkPlan que contiene toda la informacion
     * que se desea modificar de la estrategia.
     * @param oldWorkPlanKey Contiene la clavee del plan de trabajo que se desea modificar
     * @return successfulUpdate Contiene el numero que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos
     */

    @Override
    public int modifyWorkPlan(WorkPlan newWorkPlan, String oldWorkPlanKey) throws SQLException {
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE workplan set endingDate = ?, objective = ?, startDate = ?, workPlanKey = ? where workPlanKey = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        java.sql.Date endingDate = new java.sql.Date(newWorkPlan.getEndingDate().getTime());
        statement.setDate(1, endingDate);
        statement.setString(2, newWorkPlan.getObjective());
        java.sql.Date starDate = new java.sql.Date(newWorkPlan.getStartDate().getTime());
        statement.setDate(3, starDate);
        statement.setString(4, newWorkPlan.getWorkPlanKey());
        statement.setString(5, oldWorkPlanKey);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    /**
     *
     * @param workPlanKey Contiene la clave del plan de trabajo que se desea eliminar
     * @return successfulUpdate Contiene el numero que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos
     */

    @Override
    public int deleteWorkPlan(String workPlanKey) throws SQLException {
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM workplan WHERE workPlanKey = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, workPlanKey);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    /**
     *
     * @return allWorkPlans Lista con todas los planes de trabajo encontrados en la base de datos.
     * @throws SQLException se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos.
     */

    @Override
    public ArrayList<WorkPlan> getAllWorkPlans () throws SQLException {
        WorkPlan workPlan = null;
        ArrayList<WorkPlan> allWorkPlans = new ArrayList<>();
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM workplan";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet results = preparedStatement.executeQuery();
        while (results.next()) {
            workPlan = new WorkPlan();
            workPlan.setWorkPlanKey(results.getString("workPlanKey"));
            java.util.Date dateStart = new java.util.Date(results.getDate("startDate").getTime());
            workPlan.setStartDate(dateStart);
            java.util.Date dateEnd = new java.util.Date(results.getDate("endingDate").getTime());
            workPlan.setEndingDate(dateEnd);
            workPlan.setObjective(results.getString("objective"));
            allWorkPlans.add(workPlan);
        }
        return allWorkPlans;
    }
}
