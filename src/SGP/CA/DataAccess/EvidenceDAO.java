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
        String query = "INSERT INTO evidence (description, filePath) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, evidence.getDescription());
        statement.setString(2, evidence.getFilePath());
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

    @Override
    public Evidence searchEvidenceByIDEvidence(int idEvidence) throws SQLException, ClassNotFoundException {
        Evidence evidence = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * from evidence WHERE idEvidence = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idEvidence);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            evidence = new Evidence();
            evidence.setIdEvidence(results.getInt("idEvidence"));
            evidence.setDescription(results.getString("description"));
            evidence.setFilePath(results.getString("filePath"));
        }
        return evidence;
    }

    @Override
    public ArrayList<Evidence> getAllEvidence() throws SQLException, ClassNotFoundException {
        ArrayList<Evidence> allEvidences = new ArrayList<>();
        Evidence evidence = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * from evidence";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            evidence = new Evidence();
            evidence.setIdEvidence(results.getInt("idEvidence"));
            evidence.setDescription(results.getString("description"));
            evidence.setFilePath(results.getString("filePath"));
            allEvidences.add(evidence);
        }
        return allEvidences;
    }
}
