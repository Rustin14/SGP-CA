package SGP.CA.Domain;

public class Evidence {

    private int idEvidence;
    private String evidenceName;
    private String description;
    private String filePath;
    private String typeOfEvidence;


    public Evidence() {}

    public Evidence(int idEvidence, String evidenceName, String description, String filePath, String typeOfEvidence) {
        this.idEvidence = idEvidence;
        this.evidenceName = evidenceName;
        this.description = description;
        this.filePath = filePath;
        this.typeOfEvidence = typeOfEvidence;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTypeOfEvidence() {
        return typeOfEvidence;
    }

    public void setTypeOfEvidence(String typeOfEvidence) {
        this.typeOfEvidence = typeOfEvidence;
    }

    public String getEvidenceName() {
        return evidenceName;
    }

    public void setEvidenceName(String evidenceName) {
        this.evidenceName = evidenceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdEvidence() {
        return idEvidence;
    }

    public void setIdEvidence(int idEvidence) {
        this.idEvidence = idEvidence;
    }
}
