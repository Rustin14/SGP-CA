import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.Member;
import java.sql.SQLException;

public class MuestraDAO {

    public static void main(String[] args) {
        MemberDAO memberDAO = new MemberDAO();

        //Registro de miembro
        /*Member member = new Member();
        member.setName("Gabriel");
        member.setFirstLastName("Flores");
        member.setSecondLastName("Lira");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1997, 5, 19);
        member.setDateOfBirth(calendar.getTime());
        member.setEmail("gabofl14@gmail.com");
        member.setPassword("123456");

        try {
            memberDAO.saveMember(member);
            System.out.println("¡Registro realizado!");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        */

        //Consulta de Miembro
        Member consultaMiembro = new Member();
        try {
            consultaMiembro = memberDAO.searchMemberByName("Gabriel");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        System.out.println("--- CONSULTA A BASE DE DATOS --");
        System.out.println("ID: " + consultaMiembro.getIdMember());
        System.out.println("Nombre: " + consultaMiembro.getName());
        System.out.println("Apellido paterno: " + consultaMiembro.getFirstLastName());
        System.out.println("Apellido materno: " + consultaMiembro.getSecondLastName());
        System.out.println("Correo: " + consultaMiembro.getEmail());
        System.out.println("Fecha de nacimiento: " + consultaMiembro.getDateOfBirth().toString());

    }
}
