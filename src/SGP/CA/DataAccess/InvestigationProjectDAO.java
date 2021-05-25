package SGP.CA.DataAccess;

import SGP.CA.Domain.InvestigationProject;
import SGP.CA.DataAccess.Interfaces.IInvestigationProjectDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class InvestigationProjectDAO implements IInvestigationProjectDAO{

    @Override
    public int saveInvestigationProject(InvestigationProject investigationProject) throws SQLException, ClassNotFoundException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO investigationProject (associatedLgac, estimatedEndDate, participants, projectTitle, " +
                "startDate, projectManager, description) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, investigationProject.getAssociatedLgac());
        java.sql.Date sqlEstimatedEndDate= new java.sql.Date(investigationProject.getEstimatedEndDate().getTime());
        statement.setDate(2, sqlEstimatedEndDate);
        statement.setString(3, investigationProject.getParticipants());
        statement.setString(4, investigationProject.getProjectTitle());
        java.sql.Date sqlStartDate= new java.sql.Date(investigationProject.getStartDate().getTime());
        statement.setDate(5, sqlStartDate);
        statement.setString(6, investigationProject.getProjectManager());
        statement.setString(7,investigationProject.getDescription());
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    @Override
    public InvestigationProject searchInvestigationProjectByTitle(String investigationProjectTitle) throws SQLException, ClassNotFoundException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM investigationProject WHERE projectTitle = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, investigationProjectTitle);
        ResultSet results = preparedStatement.executeQuery();
        InvestigationProject investigationProject = null;
        while (results.next()) {
            investigationProject = new InvestigationProject();
            investigationProject.setAssociatedLgac(results.getString("associatedLgac"));
            Date estimatedEndDate = new Date(results.getDate("estimatedEndDate").getTime());
            investigationProject.setEstimatedEndDate(estimatedEndDate);
            investigationProject.setParticipants(results.getString("participants"));
            investigationProject.setProjectTitle(results.getString("projectTitle"));
            Date startDate = new Date(results.getDate("startDate").getTime());
            investigationProject.setStartDate(startDate);
            investigationProject.setProjectManager(results.getString("projectManager"));
            investigationProject.setDescription(results.getString("description"));
        }
        dataBaseConnection.closeConnection();
        return investigationProject;
    }

    @Override
    public int modifyInvestigationProject (InvestigationProject newInvestigationProject, String oldInvestigationProjectTitle) throws SQLException, ClassNotFoundException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE investigationProject set associatedLgac = ?, estimatedEndDate = ?, participants = ?, projectTitle = ?, "+
                "startDate = ?, projectManager = ?, description = ? where projectTitle = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newInvestigationProject.getAssociatedLgac());
        java.sql.Date sqlEstimatedEndDate= new java.sql.Date(newInvestigationProject.getEstimatedEndDate().getTime());
        statement.setDate(2, sqlEstimatedEndDate);
        statement.setString(3, newInvestigationProject.getParticipants());
        statement.setString(4, newInvestigationProject.getProjectTitle());
        java.sql.Date sqlStartDate= new java.sql.Date(newInvestigationProject.getStartDate().getTime());
        statement.setDate(5, sqlStartDate);
        statement.setString(6, newInvestigationProject.getProjectManager());
        statement.setString(7, newInvestigationProject.getDescription());
        statement.setString(8, oldInvestigationProjectTitle);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    @Override
    public int deleteInvestigationProject(String investigationProjectTitle) throws SQLException, ClassNotFoundException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM investigationProject WHERE projectTitle = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, investigationProjectTitle);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    @Override
    public ArrayList<InvestigationProject> getAllInvestigationProjects () throws SQLException, ClassNotFoundException{
        InvestigationProject investigationProject = null;
        ArrayList<InvestigationProject> allInvestigationProjects = new ArrayList<>();
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM investigationProject";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet results = preparedStatement.executeQuery();
        while (results.next()){
            investigationProject = new InvestigationProject();
            investigationProject.setProjectTitle(results.getString("projectTitle"));
            investigationProject.setParticipants(results.getString("participants"));
            investigationProject.setAssociatedLgac(results.getString("associatedLgac"));
            java.util.Date dateStart = new java.util.Date(results.getDate("startDate").getTime());
            investigationProject.setStartDate(dateStart);
            java.util.Date dateEnd = new java.util.Date(results.getDate("estimatedEndDate").getTime());
            investigationProject.setEstimatedEndDate(dateEnd);
            investigationProject.setProjectManager(results.getString("projectManager"));
            investigationProject.setDescription(results.getString("description"));
            allInvestigationProjects.add(investigationProject);
        }
        return allInvestigationProjects;
    }
}
