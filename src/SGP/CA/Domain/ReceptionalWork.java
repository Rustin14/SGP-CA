package SGP.CA.Domain;

public class ReceptionalWork extends Evidence {

    private int idWork;
    private String workName;
    private String authors;
    private int idEvidence;

    public ReceptionalWork() {

    }

    public ReceptionalWork(int idWork, String workName, String authors, int idEvidence) {
        this.idWork = idWork;
        this.workName = workName;
        this.authors = authors;
        this.idEvidence = idEvidence;
    }

    public ReceptionalWork(int idEvidence, String description, String filePath, int idWork, String workName, String authors, int idEvidence1) {
        super(idEvidence, description, filePath);
        this.idWork = idWork;
        this.workName = workName;
        this.authors = authors;
        this.idEvidence = idEvidence1;
    }

    public int getIdWork() {
        return idWork;
    }

    public void setIdWork(int idWork) {
        this.idWork = idWork;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    @Override
    public int getIdEvidence() {
        return idEvidence;
    }

    @Override
    public void setIdEvidence(int idEvidence) {
        this.idEvidence = idEvidence;
    }
}
