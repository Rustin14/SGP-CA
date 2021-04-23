package SGP.CA.Domain;

import java.util.Date;

public class Colaborator extends Member {

    public Colaborator() {

    }

    public Colaborator(int idMember, String name, String firstLastName, String secondLastName, Date dateOfBirth, int age, String email, String password) {
        super(idMember, name, firstLastName, secondLastName, dateOfBirth, age, email, password);
    }
}
