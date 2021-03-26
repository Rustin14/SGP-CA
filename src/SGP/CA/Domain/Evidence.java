package SGP.CA.Domain;

public class Evidence {

    private String filePath;
    private String typeOfEvidence;

    public Evidence() {
    }

    public Evidence(String filePath, String typeOfEvidence) {
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
}
