package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Member;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IMemberDAO {
    public int saveMember(Member member) throws SQLException, ClassNotFoundException;
    public Member searchMemberByName(String name) throws SQLException, ClassNotFoundException;
    public ArrayList<Member> getAllMembers() throws SQLException, ClassNotFoundException;
}
