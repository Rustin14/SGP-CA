package SGP.CA.Test;

import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.Member;
import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDAOTest {

    MemberDAO memberDAO = new MemberDAO();

    @Test
    public void saveMemberTest() throws SQLException, ClassNotFoundException {
        Member member = new Member();
        member.setName("Test");
        member.setFirstLastName("Test");
        member.setSecondLastName("Test");
        member.setEmail("Test");
        member.setPassword("Test");
        memberDAO.saveMember(member);

        ArrayList<Member> allMembers = memberDAO.getAllMembers();
        Assert.assertEquals(member.getName(), allMembers.get(1).getName());
    }

    @Test
    public void searchMemberByNameTest () throws SQLException {
        Member member = memberDAO.searchMemberByName("Gabriel");

        Assert.assertEquals("Gabriel", member.getName());
    }

    @Test
    public void getAllMembersTest () throws SQLException {
        ArrayList<Member> allMembers = memberDAO.getAllMembers();

        Assert.assertEquals("Gabriel", allMembers.get(0).getName());
    }
}
