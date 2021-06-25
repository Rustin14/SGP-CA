package SGP.CA.Domain;

import java.util.Date;

public class InvestigationProject {

    private String projectTitle;
    private Date estimatedEndDate;
    private Date startDate;
    private String associatedLgac;
    private String participants;
    private String projectManager;
    private String description;

    public InvestigationProject() {
        this.projectTitle = "";
        this.estimatedEndDate = new Date();
        this.startDate = new Date();
        this.associatedLgac = "";
        this.participants = "";
        this.projectManager = "";
        this.description = "";
    }

    public InvestigationProject(String projectTitle, Date estimatedEndDate, Date startDate, String associatedLgac, String participants,
                                String projectManager, String description) {
        this.projectTitle = projectTitle;
        this.estimatedEndDate = estimatedEndDate;
        this.startDate = startDate;
        this.associatedLgac = associatedLgac;
        this.participants = participants;
        this.projectManager = projectManager;
        this.description = description;
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

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getProjectManager() {
        return projectManager;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "projectTitle: " + projectTitle + '\n' +
                "estimatedEndDate: " + estimatedEndDate.toString() + '\n' +
                "startDate: " + startDate.toString() + '\n' +
                "associatedLgac: " + associatedLgac + '\n' +
                "participants: " + participants + '\n';
    }

    @Override
    public boolean equals(Object o) {
        boolean iguales = false;
        if (this.getClass() == o.getClass()) {
            InvestigationProject investigationProjectToCompare = (InvestigationProject) o;
            if (this.getProjectTitle().equals(investigationProjectToCompare.getProjectTitle()) &&
                this.getEstimatedEndDate().equals(investigationProjectToCompare.getEstimatedEndDate()) &&
                this.getStartDate().equals(investigationProjectToCompare.getStartDate()) &&
                this.getAssociatedLgac().equals(investigationProjectToCompare.getAssociatedLgac()) &&
                this.getParticipants().equals(investigationProjectToCompare.getParticipants()) &&
                this.getProjectManager().equals(investigationProjectToCompare.getProjectManager()) &&
                this.getDescription().equals(investigationProjectToCompare.getDescription())) {
                iguales= true;
            }
        }
        return iguales;
    }
}
