package SGP.CA.Domain;

import java.util.Date;

public class InvestigationProject {

    private String projectTitle;
    private Date estimatedEndDate;
    private Date startDate;
    private String associatedLgac;
    private String participants;

    public InvestigationProject() {
        this.projectTitle = "";
        this.estimatedEndDate = new Date();
        this.startDate = new Date();
        this.associatedLgac = "";
        this.participants = "";
    }

    public InvestigationProject(String projectTitle, Date estimatedEndDate, Date startDate, String associatedLgac, String participants) {
        this.projectTitle = projectTitle;
        this.estimatedEndDate = estimatedEndDate;
        this.startDate = startDate;
        this.associatedLgac = associatedLgac;
        this.participants = participants;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEstimatedEndDate(Date estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    public void setAssociatedLgac(String associatedLgac) {
        this.associatedLgac = associatedLgac;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public Date getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getAssociatedLgac() {
        return associatedLgac;
    }

    public String getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "projectTitle: " + projectTitle + '\n' +
                "estimatedEndDate: " + estimatedEndDate.toString() + '\n' +
                "startDate: " + startDate.toString() + '\n' +
                "associatedLgac: " + associatedLgac + '\n' +
                "participants: " + participants + '\n';
    }
}
