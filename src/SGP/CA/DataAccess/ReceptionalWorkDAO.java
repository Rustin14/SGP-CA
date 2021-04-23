package SGP.CA.DataAccess;
import SGP.CA.DataAccess.Interfaces.IReceptionalWorkDAO;
import SGP.CA.Domain.ReceptionalWork;
import java.sql.*;
import java.util.ArrayList;

public class ReceptionalWorkDAO implements IReceptionalWorkDAO {

    @Override
    public int saveReceptionalWork(ReceptionalWork receptionalWork) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO receptionalwork (workName, authors, idEvidence) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, receptionalWork.getWorkName());
        statement.setString(2, receptionalWork.getAuthors());
        statement.setInt(3, receptionalWork.getIdEvidence());
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

    @Override
    public ReceptionalWork searchWorkByName(String workName) throws SQLException {
        ReceptionalWork work = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM receptionalwork WHERE workName LIKE '%?%'";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, workName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            work = new ReceptionalWork();
            work.setIdWork(resultSet.getInt("idWork"));
            work.setWorkName(resultSet.getString("workName"));
            work.setAuthors(resultSet.getString("authors"));
            work.setIdEvidence(resultSet.getInt("idEvidence"));
        }
        return work;
    }

    @Override
    public ArrayList<ReceptionalWork> getAllWorks() throws SQLException {
        ArrayList<ReceptionalWork> allWorks = new ArrayList<>();
        ReceptionalWork work = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM receptionalwork";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            work = new ReceptionalWork();
            work.setIdWork(resultSet.getInt("idWork"));
            work.setWorkName(resultSet.getString("workName"));
            work.setAuthors(resultSet.getString("authors"));
            work.setIdEvidence(resultSet.getInt("idEvidence"));
            allWorks.add(work);
        }
        return allWorks;
    }
}
