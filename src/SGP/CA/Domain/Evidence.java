package SGP.CA.Domain;

import java.util.Date;

public class Evidence {

    private int idEvidence;
    private String evidenceTitle;
    private String evidenceType;
    private String description;
    private Date registrationDate;
    private Date modificationDate;
    private int idMember;
    private int active;

    public static Evidence selectedEvidence;


    public Evidence() {}

    public Evidence(String evidenceTitle, String evidenceType, int idEvidence, String description, int active) {
        this.evidenceTitle = evidenceTitle;
        this.evidenceType = evidenceType;
        this.idEvidence = idEvidence;
        this.description = description;
        this.active = active;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getEvidenceTitle() {
        return evidenceTitle;
    }

    public void setEvidenceTitle(String evidenceTitle) {
        this.evidenceTitle = evidenceTitle;
    }

    public String getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public static Evidence getSelectedEvidence() {
        return selectedEvidence;
    }

    public static void setSelectedEvidence(Evidence selectedEvidence) {
        Evidence.selectedEvidence = selectedEvidence;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }
}
