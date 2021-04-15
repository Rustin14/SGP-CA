package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IWorkPlanDAO;
import java.util.Date;
import SGP.CA.Domain.WorkPlan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkPlanDAO implements IWorkPlanDAO {

    @Override
    public int saveWorkPlan(WorkPlan workPlan) throws SQLException, ClassNotFoundException{
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

    @Override
    public WorkPlan searchWorkPlanByWorkPlanKey (String workPlanKey) throws SQLException, ClassNotFoundException{
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

    @Override
    public int modifyWorkPlan(WorkPlan newWorkPlan, String oldWorkPlanKey) throws SQLException, ClassNotFoundException{
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
}
