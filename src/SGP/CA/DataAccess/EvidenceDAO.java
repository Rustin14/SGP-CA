package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IEvidenceDAO;
import SGP.CA.Domain.Evidence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EvidenceDAO implements IEvidenceDAO {

    @Override
    public int saveEvidence(Evidence evidence) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO evidence (evidenceName, description, typeOfEvidence, registrationDate) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, evidence.getEvidenceTitle());
        statement.setString(2, evidence.getDescription());
        statement.setString(3, evidence.getEvidenceType());
        java.sql.Date sqlRegistrationDate = new java.sql.Date(evidence.getRegistrationDate().getTime());
        statement.setDate(4, sqlRegistrationDate);
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

    public int deleteEvidence(int idEvidence) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "UPDATE evidence SET active = 0 WHERE idEvidence = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idEvidence);
        int databaseResponse = preparedStatement.executeUpdate();
        return databaseResponse;
    }

    @Override
    public Evidence searchEvidenceByIDEvidence(int idEvidence) throws SQLException{
        Evidence evidence = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * from evidence WHERE idEvidence = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idEvidence);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            evidence = new Evidence();
            evidence.setEvidenceTitle(results.getString("evidenceName"));
            evidence.setIdEvidence(results.getInt("idEvidence"));
            evidence.setDescription(results.getString("description"));
            evidence.setEvidenceType(results.getString("evidenceType"));
        }
        return evidence;
    }

    @Override
    public ArrayList<Evidence> getAllEvidence() throws SQLException{
        ArrayList<Evidence> allEvidences = new ArrayList<>();
        Evidence evidence = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * from evidence";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            evidence = new Evidence();
            evidence.setEvidenceTitle(results.getString("evidenceName"));
            evidence.setIdEvidence(results.getInt("idEvidence"));
            evidence.setDescription(results.getString("description"));
            evidence.setEvidenceType(results.getString("typeOfEvidence"));
            evidence.setActive(results.getInt("active"));
            allEvidences.add(evidence);
        }
        return allEvidences;
    }
}
