package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IMemberDAO;
import SGP.CA.Domain.Member;

import java.sql.SQLException;

public class MemberDAO implements IMemberDAO {

    @Override
    public Member searchMemberByName(String name) throws SQLException {
        return null;
    }

    @Override
    public Member getAllMembers() throws SQLException {
        return null;
    }
}
