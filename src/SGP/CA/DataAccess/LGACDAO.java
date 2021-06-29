/**
    @author Gabriel Flores
 */

package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.ILGACDAO;
import SGP.CA.Domain.LGAC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LGACDAO implements ILGACDAO {



    /**
        *@param lineName Nombre de LGAC que se desea encontrar.
        *@return LGAC lgac Si encuentra una coincidencia con el nombre que fue mandado,
        *regresa el objeto LGAC encontrado.
        *@throws SQLException Se cacha una SQLException en caso de un posible error de conexión
        * a la base de datos.
     */

    @Override
    public LGAC searchLGACbyLineName(String lineName) throws SQLException {
        LGAC lgac = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM lgac WHERE lineName LIKE '%" + lineName + "%'";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            lgac = new LGAC();
            lgac.setIdLGAC(resultSet.getInt("idLGAC"));
            lgac.setLineName(resultSet.getString("lineName"));
        }
        return lgac;
    }

    /**
     * @param idLGAC Se manda como parámetro el ID de la LGAC que deseas encontrar.
     * @return LGAC lgac En caso de encontrar coincidencias con el ID proporcionado,
     * regresará un objeto LGAC.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    @Override
    public LGAC searchLGACbyID(int idLGAC) throws SQLException {
        LGAC lgac = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM lgac WHERE idLGAC = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idLGAC);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            lgac = new LGAC();
            lgac.setIdLGAC(resultSet.getInt("idLGAC"));
            lgac.setLineName(resultSet.getString("lineName"));
        }
        return lgac;
    }

    /**
     * @return allLines Regresa una lista con todos las LGAC
     * que se encuentran almacenadas en la base de datos.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    @Override
    public ArrayList<LGAC> getAllLines() throws SQLException {
        LGAC lgac = null;
        ArrayList<LGAC> allLines = new ArrayList<>();
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM lgac";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            lgac = new LGAC();
            lgac.setIdLGAC(resultSet.getInt("idLGAC"));
            lgac.setLineName(resultSet.getString("lineName"));
            allLines.add(lgac);
        }
        return allLines;
    }
}
