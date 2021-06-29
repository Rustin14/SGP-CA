/**
 * @author Gabriel Flores
 */
package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IMemberDAO;
import SGP.CA.Domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDAO implements IMemberDAO {

    /**
     *
     * @param member Manda un objeto del tipo Member que contiene toda la información
     * que se desea guardar del Miembro.
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    @Override
    public int saveMember(Member member) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO member (name, firstLastName, " +
                "secondLastName, dateOfBirth, phoneNumber, CURP, maximumStudyLevel, " +
                "maximumStudyLevelInstitution, idLGAC, email, password, isResponsible) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, member.getName());
        statement.setString(2, member.getFirstLastName());
        statement.setString(3, member.getSecondLastName());
        java.sql.Date sqlDateOfBirth = new java.sql.Date(member.getDateOfBirth().getTime());
        statement.setDate(4,  sqlDateOfBirth);
        statement.setString(5, member.getPhoneNumber());
        statement.setString(6, member.getCURP());
        statement.setString(7, member.getMaximumStudyLevel());
        statement.setString(8, member.getMaximumStudyLevelInstitution());
        statement.setInt(9, member.getIdLGAC());
        statement.setString(10, member.getEmail());
        statement.setString(11, member.getPassword());
        statement.setInt(12, member.getIsResponsible());
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

    /**
     *
     * @param member Manda un objeto del tipo Member que contiene toda la información
     * que se desea modificar del Miembro.
     * @param idMember Contiene el ID del Miembro que se desea modificar.
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    public int modifyMember (Member member, int idMember) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "UPDATE member SET name = ?, firstLastName = ?, secondLastName = ?, dateOfBirth = ?, phoneNumber = ?, " +
                "curp = ?, maximumStudyLevel = ?, maximumStudyLevelInstitution = ?, idLGAC = ?, email = ? WHERE idMember = " + idMember;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, member.getName());
        statement.setString(2, member.getFirstLastName());
        statement.setString(3, member.getSecondLastName());
        java.sql.Date sqlDateOfBirth = new java.sql.Date(member.getDateOfBirth().getTime());
        statement.setDate(4,  sqlDateOfBirth);
        statement.setString(5, member.getPhoneNumber());
        statement.setString(6, member.getCURP());
        statement.setString(7, member.getMaximumStudyLevel());
        statement.setString(8, member.getMaximumStudyLevelInstitution());
        statement.setInt(9, member.getIdLGAC());
        statement.setString(10, member.getEmail());
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

    /**
     *
     * @param name Nombre del Miembro que se desea buscar en la base de datos.
     * @return member En caso de encontrar una coincidencia, se regresa un objeto
     * de tipo Member con toda la información del miembro coincidente.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    @Override
    public Member searchMemberByName(String name) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM member WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        ResultSet results = preparedStatement.executeQuery();
        Member member = null;
        while (results.next()) {
            member = new Member();
            member.setIdMember(results.getInt("idMember"));
            member.setName(results.getString("name"));
            member.setFirstLastName(results.getString("firstLastName"));
            member.setSecondLastName(results.getString("secondLastName"));
            java.util.Date dateOfBirth = new java.util.Date(results.getDate("dateOfBirth").getTime());
            member.setPhoneNumber(results.getString("phoneNumber"));
            member.setCURP(results.getString("CURP"));
            member.setMaximumStudyLevel(results.getString("maximumStudyLevel"));
            member.setMaximumStudyLevelInstitution(results.getString("maximumStudyLevelInstitution"));
            member.setIdLGAC(results.getInt("idLGAC"));
            member.setEmail(results.getString("email"));
            member.setPassword(results.getString("password"));
            member.setDateOfBirth(dateOfBirth);
            member.setEmail(results.getString("email"));
            member.setPassword(results.getString("password"));
            member.setActive(results.getInt("active"));
            member.setIsResponsible(results.getInt("isResponsible"));
        }
        return member;
    }

    /**
     *
     * @return allMembers Lista con todos los Miembros encontrados en la base de datos.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    @Override
    public ArrayList<Member> getAllMembers() throws SQLException {
        Member member = null;
        ArrayList<Member> allMembers = new ArrayList<>();
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM member";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet results = preparedStatement.executeQuery();
        while (results.next()) {
            member = new Member();
            member.setIdMember(results.getInt("idMember"));
            member.setName(results.getString("name"));
            member.setFirstLastName(results.getString("firstLastName"));
            member.setSecondLastName(results.getString("secondLastName"));
            java.util.Date dateOfBirth = new java.util.Date(results.getDate("dateOfBirth").getTime());
            member.setPhoneNumber(results.getString("phoneNumber"));
            member.setCURP(results.getString("CURP"));
            member.setMaximumStudyLevel(results.getString("maximumStudyLevel"));
            member.setMaximumStudyLevelInstitution(results.getString("maximumStudyLevelInstitution"));
            member.setIdLGAC(results.getInt("idLGAC"));
            member.setEmail(results.getString("email"));
            member.setPassword(results.getString("password"));
            member.setDateOfBirth(dateOfBirth);
            member.setActive(results.getInt("active"));
            member.setIsResponsible(results.getInt("isResponsible"));
            allMembers.add(member);
        }
        return allMembers;
    }

    /**
     *
     * @param idMember Contiene el ID del Miembro que se desea eliminar.
     * @return successfulUpdate Contiene el número que indica si el guardado de datos fue exitoso.
     * 1 indica exitoso. 0 indica que no fue posible hacer el guardado.
     * @throws SQLException Se cacha una SQLException en caso de un posible error de conexión
     * a la base de datos.
     */

    public int deleteMember(int idMember) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "UPDATE member SET active = 0 WHERE idMember = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idMember);
        int databaseResponse = preparedStatement.executeUpdate();
        return databaseResponse;
    }

}
