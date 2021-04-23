package SGP.CA.Domain;

public class Prototype extends Evidence {

    private int idPrototype;
    private String prototypeName;

    public Prototype() {
    }

    public Prototype(int idPrototype, int idEvidence, String prototypeName, String description, String filePath) {
        super(idEvidence, description, filePath);
        this.idPrototype = idPrototype;
        this.prototypeName = prototypeName;
    }

    public int getIdPrototype() {
        return idPrototype;
    }

    public void setIdPrototype(int idPrototype) {
        this.idPrototype = idPrototype;
    }

    public String getPrototypeName() {
        return prototypeName;
    }

    public void setPrototypeName(String prototypeName) {
        this.prototypeName = prototypeName;
    }
}
