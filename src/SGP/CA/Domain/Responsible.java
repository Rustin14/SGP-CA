package SGP.CA.Domain;

import java.util.Date;

public class Responsible extends Member {

    private int idResponsible;

    public Responsible() {
    }

    public Responsible(int idResponsible) {
        this.idResponsible = idResponsible;
    }

    public Responsible(int idMember, String name, String firstLastName, String secondLastName, Date dateOfBirth, int age, String email, String password, int idResponsible) {
        super(idMember, name, firstLastName, secondLastName, dateOfBirth, age, email, password);
        this.idResponsible = idResponsible;
    }

    public int getIdResponsible() {
        return idResponsible;
    }

    public void setIdResponsible(int idResponsible) {
        this.idResponsible = idResponsible;
    }
}
