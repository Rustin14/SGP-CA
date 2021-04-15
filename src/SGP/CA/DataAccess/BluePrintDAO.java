package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IBluePrintDAO;
import SGP.CA.Domain.BluePrint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BluePrintDAO implements  IBluePrintDAO{

    @Override
    public int saveBluePrint(BluePrint bluePrint) throws SQLException, ClassNotFoundException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO blueprint (blueprintTitle, startDate, associatedLgac, state, " +
                "coDirector, duration, modality, student, description) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, bluePrint.getBluePrintTitle());
        java.sql.Date sqlStartDate= new java.sql.Date(bluePrint.getStartDate().getTime());
        statement.setDate(2, sqlStartDate);
        statement.setString(3, bluePrint.getAssociatedLgac());
        statement.setString(4, bluePrint.getState());
        statement.setString(5, bluePrint.getCoDirector());
        statement.setInt(6, bluePrint.getDuration());
        statement.setString(7, bluePrint.getModality());
        statement.setString(8, bluePrint.getStudent());
        statement.setString(9, bluePrint.getDescription());
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    @Override
    public BluePrint searchBluePrintByTitle(String bluePrintTitle) throws SQLException, ClassNotFoundException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT * FROM blueprint WHERE blueprintTitle = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, bluePrintTitle);
        ResultSet results = preparedStatement.executeQuery();
        BluePrint bluePrint = null;
        while (results.next()) {
            bluePrint = new BluePrint();
            bluePrint.setAssociatedLgac(results.getString("associatedLgac"));
            bluePrint.setBluePrintTitle(results.getString("blueprintTitle"));
            bluePrint.setCoDirector(results.getString("coDirector"));
            bluePrint.setDescription(results.getString("description"));
            bluePrint.setDuration(results.getInt("duration"));
            bluePrint.setModality(results.getString("modality"));
            Date startDate = new Date(results.getDate("startDate").getTime());
            bluePrint.setStartDate(startDate);
            bluePrint.setState(results.getString("state"));
            bluePrint.setStudent(results.getString("student"));
        }
        dataBaseConnection.closeConnection();
        return bluePrint;
    }

    @Override
    public int modifyBluePrint(BluePrint newBluePrint, String oldBluePrintTitle) throws SQLException, ClassNotFoundException{
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE blueprint set associatedLgac = ?, blueprintTitle = ?, coDirector = ?, "+
                "description = ?, duration = ?, modality = ?, startDate = ?, state = ?, student = ? "+
                "where blueprintTitle = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newBluePrint.getAssociatedLgac());
        statement.setString(2, newBluePrint.getBluePrintTitle());
        statement.setString(3, newBluePrint.getCoDirector());
        statement.setString(4, newBluePrint.getDescription());
        statement.setInt(5, newBluePrint.getDuration());
        statement.setString(6, newBluePrint.getModality());
        java.sql.Date sqlStartDate= new java.sql.Date(newBluePrint.getStartDate().getTime());
        statement.setDate(7, sqlStartDate);
        statement.setString(8, newBluePrint.getState());
        statement.setString(9, newBluePrint.getStudent());
        statement.setString(10, oldBluePrintTitle);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }
}
