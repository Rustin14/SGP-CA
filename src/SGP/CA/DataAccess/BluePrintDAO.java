/**
 * @autor Hector David
 */
package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IBluePrintDAO;
import SGP.CA.Domain.BluePrint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class BluePrintDAO implements  IBluePrintDAO {

    /**
     *
     * @param bluePrint manda un objeto del tipo BluePrint que contiene toda la informacion
     * que se desea guardar del Anteproyecto
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException se cacha una SQLException en un caso de un posible error de conexion
     * a la base de datos.
     */

    @Override
    public int saveBluePrint(BluePrint bluePrint) throws SQLException {
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "INSERT INTO blueprint (blueprintTitle, startDate, associatedLgac, state, " +
                "coDirector, duration, modality, student, description, director, " +
                "receptionWorkName, requirements) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
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
        statement.setString(10, bluePrint.getDirector());
        statement.setString(11,bluePrint.getReceptionWorkName());
        statement.setString(12,bluePrint.getRequirements());
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    /**
     *
     * @param bluePrintTitle titulo del Anteproyecto que desea buscar en la base de datos.
     * @return bluePrint En caso de encontrar una coincidencia, se regresa un objeto
     * del tipo BluePrint con toda la informacion del anteproyecto coincidente.
     * @throws SQLException Se cacha una SQL Exception en caso de un posible error de conexion
     * a la base de datos
     */

    @Override
    public BluePrint searchBluePrintByTitle(String bluePrintTitle) throws SQLException {
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
            bluePrint.setDirector(results.getString("director"));
            bluePrint.setReceptionWorkName(results.getString("receptionWorkName"));
            bluePrint.setRequirements(results.getString("requirements"));
        }
        dataBaseConnection.closeConnection();
        return bluePrint;
    }

    /**
     *
     * @param newBluePrint Manda un objeto del tipo BluePrint que contiene toda la informacion
     * que se desea modificar del anteproyecto.
     * @param oldBluePrintTitle Contiene el titulo del Anteproyecto que se desea modificar
     * @return successfulUpdate Contiene el numero que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos
     */

    @Override
    public int modifyBluePrint(BluePrint newBluePrint, String oldBluePrintTitle) throws SQLException {
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "UPDATE blueprint set associatedLgac = ?, blueprintTitle = ?, coDirector = ?, "+
                "description = ?, duration = ?, modality = ?, startDate = ?, state = ?, student = ?, "+
                "director = ?, receptionWorkName = ?, requirements = ? where blueprintTitle = ?";
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
        statement.setString(10,newBluePrint.getDirector());
        statement.setString(11, newBluePrint.getReceptionWorkName());
        statement.setString(12,newBluePrint.getRequirements());
        statement.setString(13, oldBluePrintTitle);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    /**
     *
     * @param bluePrintTitle Contiene el titulo del Anteproyecto que se desea eliminar
     * @return successfulUpdate Contiene el numero que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos
     */

    @Override
    public int deleteBluePrint(String bluePrintTitle) throws SQLException {
        ConnectDB dataBaseConnection = new ConnectDB();
        Connection connection = dataBaseConnection.getConnection();
        String query = "DELETE FROM blueprint WHERE blueprintTitle = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, bluePrintTitle);
        int successfulUpdate = statement.executeUpdate();
        dataBaseConnection.closeConnection();
        return successfulUpdate;
    }

    /**
     *
     * @return allBluePrints Lista con todos los Anteproyectos encontrados en la base de datos.
     * @throws SQLException se cacha una SQLException en caso de un posible error de conexion
     * a la base de datos.
     */

    @Override
    public ArrayList<BluePrint> getAllBluePrints() throws SQLException {
        BluePrint bluePrint = null;
        ArrayList<BluePrint> allBluePrints = new ArrayList<>();
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM blueprint";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet results = preparedStatement.executeQuery();
        while (results.next()) {
            bluePrint = new BluePrint();
            bluePrint.setBluePrintTitle(results.getString("blueprintTitle"));
            bluePrint.setAssociatedLgac(results.getString("associatedLgac"));
            bluePrint.setDescription(results.getString("description"));
            bluePrint.setCoDirector(results.getString("coDirector"));
            bluePrint.setDuration(results.getInt("duration"));
            bluePrint.setModality(results.getString("modality"));
            bluePrint.setStudent(results.getString("student"));
            bluePrint.setState(results.getString("state"));
            java.util.Date dateStart = new java.util.Date(results.getDate("startDate").getTime());
            bluePrint.setStartDate(dateStart);
            bluePrint.setDirector(results.getString("director"));
            bluePrint.setReceptionWorkName(results.getString("receptionWorkName"));
            bluePrint.setRequirements(results.getString("requirements"));
            allBluePrints.add(bluePrint);
        }
        return allBluePrints;
    }
}
