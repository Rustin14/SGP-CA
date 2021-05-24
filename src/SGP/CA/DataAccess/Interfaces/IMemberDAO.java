package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Member;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IMemberDAO {
    public int saveMember(Member member) throws SQLException;
    public Member searchMemberByName(String name) throws SQLException;
    public ArrayList<Member> getAllMembers() throws SQLException;
}
