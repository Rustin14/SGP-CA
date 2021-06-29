/**
 * @author Gabriel Flores
 */

package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IResponsibleDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResponsibleDAO implements IResponsibleDAO {

    /**
     *
     * @param idMember ID del Miembro asociado al Responsable a almacenar.
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    @Override
    public int saveResponsible(int idMember) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO responsible (idMember) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idMember);
        int successfulUpdate = preparedStatement.executeUpdate();
        return successfulUpdate;
    }

}
