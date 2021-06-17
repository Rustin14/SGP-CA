package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IEvidenceDAO;
import SGP.CA.Domain.Evidence;
import com.mysql.cj.jdbc.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
        int successfulUpdate = preparedStatement.executeUpdate();
        return successfulUpdate;
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
            java.util.Date registrationDate = new java.util.Date(results.getDate("registrationDate").getTime());
            evidence.setRegistrationDate(registrationDate);
            java.util.Date modificationDate = new java.util.Date(results.getDate("modificationDate").getTime());
            evidence.setModificationDate(modificationDate);
            evidence.setActive(results.getInt("active"));
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
            java.util.Date registrationDate = new java.util.Date(results.getDate("registrationDate").getTime());
            evidence.setRegistrationDate(registrationDate);
            java.sql.Date sqlModificationDate = results.getDate("modificationDate");
            if (sqlModificationDate != null) {
                java.util.Date modificationDate = new java.util.Date();
                evidence.setModificationDate(modificationDate);
            }
            evidence.setActive(results.getInt("active"));
            allEvidences.add(evidence);
        }
        return allEvidences;
    }

    public int modifyEvidence(Evidence evidence, int idEvidence) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "UPDATE evidence SET evidenceName = ?, description = ?, typeOfEvidence = ?, modificationDate = ? WHERE idEvidence = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, evidence.getEvidenceTitle());
        statement.setString(2, evidence.getDescription());
        statement.setString(3, evidence.getEvidenceType());
        java.sql.Date sqlModificationDate = new java.sql.Date(evidence.getModificationDate().getTime());
        statement.setDate(4, sqlModificationDate);
        statement.setInt(5, idEvidence);
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }
}
