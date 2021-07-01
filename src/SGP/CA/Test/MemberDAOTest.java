package SGP.CA.Test;

import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.Member;
import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class MemberDAOTest {

    MemberDAO memberDAO = new MemberDAO();

    @Test
    public void saveMemberSuccesfulTest() throws SQLException {
        Member member = new Member();
        member.setName("Alan");
        member.setFirstLastName("Montano");
        member.setSecondLastName("Rodríguez");
        LocalDate dateOfBirth = LocalDate.of(2000, 6, 23);
        java.util.Date utilDateOfBirth = new Date();
        utilDateOfBirth.from(dateOfBirth.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        member.setDateOfBirth(utilDateOfBirth);
        member.setIdLGAC(1);
        member.setPhoneNumber("2281982134");
        member.setCURP("CORK980610MVZNMN05");
        member.setMaximumStudyLevel("Licenciatura");
        member.setMaximumStudyLevelInstitution("UV");
        member.setEmail("alanmontanorodriguez@uv.mx");
        member.setPassword("123456");
        member.setIsResponsible(0);
        int successfulTest = memberDAO.saveMember(member);


        Assert.assertEquals(successfulTest, 1);
    }

    @Test
    public void saveMemberCURPValidationTest()  {
        String exceptionName = "";
        Member member = new Member();
        member.setName("Alan");
        member.setFirstLastName("Montano");
        member.setSecondLastName("Rodríguez");
        LocalDate dateOfBirth = LocalDate.of(2000, 6, 23);
        java.util.Date utilDateOfBirth = new Date();
        utilDateOfBirth.from(dateOfBirth.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        member.setDateOfBirth(utilDateOfBirth);
        member.setIdLGAC(1);
        member.setPhoneNumber("2281982134");
        member.setCURP("CORK980610MVZNMN08");
        member.setMaximumStudyLevel("Licenciatura");
        member.setMaximumStudyLevelInstitution("UV");
        member.setEmail("alanmontanor@uv.mx");
        member.setPassword("123456");
        member.setIsResponsible(0);
        try {
            memberDAO.saveMember(member);
        } catch (SQLIntegrityConstraintViolationException exCURPDuplication) {
                exceptionName = exCURPDuplication.getClass().getSimpleName();
        } catch (SQLException exsqlException) {

        }
        Assert.assertEquals("SQLIntegrityConstraintViolationException", exceptionName);
    }

    @Test
    public void modifyMemberSuccessfulTest () throws SQLException{
        Member member = new Member();
        member.setIdMember(8);
        member.setName("Alan");
        member.setFirstLastName("Montano");
        member.setSecondLastName("Jiménez");
        LocalDate dateOfBirth = LocalDate.of(2000, 6, 23);
        java.util.Date utilDateOfBirth = new Date();
        utilDateOfBirth.from(dateOfBirth.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        member.setDateOfBirth(utilDateOfBirth);
        member.setIdLGAC(1);
        member.setPhoneNumber("2281982134");
        member.setCURP("CORK980610MVZNMN08");
        member.setMaximumStudyLevel("Licenciatura");
        member.setMaximumStudyLevelInstitution("UV");
        member.setEmail("alanmontanor@uv.mx");
        member.setPassword("123456");
        member.setIsResponsible(0);
        int successfulTest = memberDAO.modifyMember(member, member.getIdMember());

        Assert.assertEquals(1, successfulTest, 0);
    }

    @Test
    public void deleteMemberTest() throws SQLException {
        int successfulTest = memberDAO.deleteMember(8);

        Assert.assertEquals(1, successfulTest, 0);
    }

    @Test
    public void searchMemberByNameTest () throws SQLException {
        Member member = memberDAO.searchMemberByName("Michael");

        Assert.assertEquals("Michael", member.getName());
    }

    @Test
    public void getAllMembersTest () throws SQLException {
        ArrayList<Member> allMembers = memberDAO.getAllMembers();
        Assert.assertEquals("Michael", allMembers.get(0).getName());
    }
}
