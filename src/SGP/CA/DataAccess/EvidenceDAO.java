/**
 * @author Gabriel Flores
 */

package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IEvidenceDAO;
import SGP.CA.Domain.Evidence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EvidenceDAO implements IEvidenceDAO {

    /**
     *
     * @param evidence Objeto tipo Evidence que contiene toda la información
     * que se desea guardar en la base de datos.
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    @Override
    public int saveEvidence(Evidence evidence) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO evidence (evidenceName, description, typeOfEvidence, registrationDate, idMember) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, evidence.getEvidenceTitle());
        statement.setString(2, evidence.getDescription());
        statement.setString(3, evidence.getEvidenceType());
        java.sql.Date sqlRegistrationDate = new java.sql.Date(evidence.getRegistrationDate().getTime());
        statement.setDate(4, sqlRegistrationDate);
        statement.setInt(5, evidence.getIdMember());
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

    /**
     *
     * @param idEvidence Número entero que contiene el ID de la Evidencia
     * a eliminar.
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    public int deleteEvidence(int idEvidence) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "UPDATE evidence SET active = 0 WHERE idEvidence = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idEvidence);
        int successfulUpdate = preparedStatement.executeUpdate();
        return successfulUpdate;
    }

    /**
     *
     * @param idEvidence Número entero que contiene el ID de la Evidencia
     * que se desea buscar en la base de datos.
     * @return evidence - En caso de encontrar coincidencias, regresa un objeto
     * tipo Evidencia con toda la información de la Evidencia.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    @Override
    public Evidence searchEvidenceByIDEvidence(int idEvidence) throws SQLException {
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
            evidence.setEvidenceType(results.getString("typeOfEvidence"));
            java.util.Date registrationDate = new java.util.Date(results.getDate("registrationDate").getTime());
            evidence.setRegistrationDate(registrationDate);
            java.util.Date modificationDate = new java.util.Date(results.getDate("modificationDate").getTime());
            evidence.setModificationDate(modificationDate);
            evidence.setActive(results.getInt("active"));
            evidence.setIdMember(results.getInt("idMember"));
        }
        return evidence;
    }

    /**
     *
     * @return allEvidences Lista con todas las Evidencias encontradas en la base de datos.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    @Override
    public ArrayList<Evidence> getAllEvidence() throws SQLException {
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
            evidence.setIdMember(results.getInt("idMember"));
            allEvidences.add(evidence);
        }
        return allEvidences;
    }

    /**
     * @param idMember ID del Miembro del que se desea buscar Evidencias relacionadas.
     * @return allEvidences Lista con todas las Evidencias encontradas en la base de datos.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    public ArrayList<Evidence> getAllEvidenceFromMember(int idMember) throws SQLException {
        ArrayList<Evidence> allEvidences = new ArrayList<>();
        Evidence evidence = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * from evidence where idMember = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idMember);
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
            evidence.setIdMember(results.getInt("idMember"));
            allEvidences.add(evidence);
        }
        return allEvidences;
    }

    /**
     *
     * @param evidence Objeto tipo Evidencia que contiene todos los datos de
     * de la Evidencia que se desea modificar.
     * @param idEvidence ID de la Evidencia que se desea modificar.
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

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
