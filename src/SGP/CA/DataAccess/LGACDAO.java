package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.ILGACDAO;
import SGP.CA.Domain.*;
import java.sql.*;
import java.util.ArrayList;

public class LGACDAO implements ILGACDAO {

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
