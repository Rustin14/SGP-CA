package SGP.CA.Domain;

public class BookChapter extends Evidence {

    public BookChapter() {
    }

    public BookChapter(int idEvidence, String evidenceTitle, String evidenceType, String description, int active) {
        super(evidenceType, evidenceTitle, idEvidence, description, active);
    }
}
