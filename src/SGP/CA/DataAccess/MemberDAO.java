package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IMemberDAO;
import SGP.CA.Domain.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO implements IMemberDAO {

    @Override
    public void saveMember(Member member) throws SQLException, ClassNotFoundException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO member (name, firstLastName, " +
                "secondLastName, dateOfBirth, email, password) VALUES (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, member.getName());
        statement.setString(2, member.getFirstLastName());
        statement.setString(3, member.getSecondLastName());
        java.sql.Date sqlDateOfBirth = new java.sql.Date(member.getDateOfBirth().getTime());
        statement.setDate(4,  sqlDateOfBirth);
        statement.setString(5, member.getEmail());
        statement.setString(6, member.getPassword());
        statement.executeUpdate();
    }

    @Override
    public Member searchMemberByName(String name) throws SQLException, ClassNotFoundException {
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
    public Member getAllMembers() throws SQLException {
        return null;
    }

}
