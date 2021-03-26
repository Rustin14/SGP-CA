package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Member;

import java.sql.SQLException;

public interface IMemberDAO {
    public void saveMember(Member member) throws SQLException, ClassNotFoundException;
    public Member searchMemberByName(String name) throws SQLException, ClassNotFoundException;
    public Member getAllMembers() throws SQLException, ClassNotFoundException;
}
