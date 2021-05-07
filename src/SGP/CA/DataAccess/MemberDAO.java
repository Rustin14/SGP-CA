package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IMemberDAO;
import SGP.CA.Domain.Member;
import java.sql.*;
import java.util.ArrayList;

public class MemberDAO implements IMemberDAO {

    @Override
    public int saveMember(Member member) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO member (name, firstLastName, " +
                "secondLastName, dateOfBirth, phoneNumber, CURP, maximumStudyLevel, " +
                "maximumStudyLevelInstitution, idLGAC, email, password) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
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
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

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
            member.setDateOfBirth(dateOfBirth);
            member.setEmail(results.getString("email"));
            member.setPassword(results.getString("password"));
        }
        return member;
    }

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
            member.setEmail(results.getString("email"));
            member.setPassword(results.getString("password"));
            allMembers.add(member);
        }
        return allMembers;
    }

}
