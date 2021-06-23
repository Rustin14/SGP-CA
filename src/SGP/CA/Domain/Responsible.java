package SGP.CA.Domain;
public class Responsible extends Member {

    private int idResponsible;
    private int idMember;

    public Responsible() {
    }

    public int getIdResponsible() {
        return idResponsible;
    }

    public void setIdResponsible(int idResponsible) {
        this.idResponsible = idResponsible;
    }

    @Override
    public int getIdMember() {
        return idMember;
    }

    @Override
    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }
}
