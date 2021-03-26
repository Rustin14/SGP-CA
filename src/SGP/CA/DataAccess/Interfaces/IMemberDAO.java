package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Member;

import java.sql.SQLException;

public interface IMemberDAO {
    public Member searchMemberByName(String name) throws SQLException;
    public Member getAllMembers() throws SQLException;
}
