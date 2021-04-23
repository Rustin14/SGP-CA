package SGP.CA.Domain;

public class Evidence {

    private int idEvidence;
    private String description;
    private String filePath;


    public Evidence() {}

    public Evidence(int idEvidence, String description, String filePath) {
        this.idEvidence = idEvidence;
        this.description = description;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
