package SGP.CA.Domain;

public class LGAC {

    private String lineName;
    private int idLGAC;

    public LGAC() {
    }

    public LGAC(int idLGAC, String lineName) {
        this.idLGAC = idLGAC;
        this.lineName = lineName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }
}
