package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.ILGACDAO;
import SGP.CA.Domain.*;
import java.sql.*;
import java.util.ArrayList;

public class LGACDAO implements ILGACDAO {


    @Override
    public int saveLGAC(LGAC lgac) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO lgac (lineName) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, lgac.getLineName());
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

    @Override
    public LGAC searchLGACbyLineName(String lineName) throws SQLException {
        LGAC lgac = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM lgac WHERE lineName LIKE '%?%'";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, lineName);
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
