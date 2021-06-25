package SGP.CA.Domain;

import java.util.Date;

public class BluePrint {

    private String bluePrintTitle;
    private Date startDate;
    private String associatedLgac;
    private String state;
    private String director;
    private String coDirector;
    private int duration;
    private String modality;
    private String receptionWorkName;
    private String requirements;
    private String student;
    private String description;

    public BluePrint() {
        this.bluePrintTitle = "";
        this.startDate = new Date();
        this.associatedLgac = "";
        this.state = "";
        this.director = "";
        this.coDirector = "";
        this.duration = 0;
        this.modality = "";
        this.receptionWorkName = "";
        this.requirements = "";
        this.student = "";
        this.description = "";
    }

    public BluePrint(String bluePrintTitle, Date startDate, String associatedLgac, String state, String coDirector, int duration,
                     String modality, String student, String description, String director, String receptionWorkName, String requirements) {
        this.bluePrintTitle = bluePrintTitle;
        this.startDate = startDate;
        this.associatedLgac = associatedLgac;
        this.state = state;
        this.director = director;
        this.coDirector = coDirector;
        this.duration = duration;
        this.modality = modality;
        this.receptionWorkName = receptionWorkName;
        this.requirements = requirements;
        this.student = student;
        this.description = description;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setReceptionWorkName(String receptionWorkName) {
        this.receptionWorkName = receptionWorkName;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public void setBluePrintTitle(String bluePrintTitle) {
        this.bluePrintTitle = bluePrintTitle;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setAssociatedLgac(String associatedLgac) {
        this.associatedLgac = associatedLgac;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCoDirector(String coDirector) {
        this.coDirector = coDirector;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBluePrintTitle() {
        return bluePrintTitle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getAssociatedLgac() {
        return associatedLgac;
    }

    public String getState() {
        return state;
    }

    public String getCoDirector() {
        return coDirector;
    }

    public int getDuration() {
        return duration;
    }

    public String getModality() {
        return modality;
    }

    public String getStudent() {
        return student;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getReceptionWorkName() {
        return receptionWorkName;
    }

    public String getRequirements() {
        return requirements;
    }

    @Override
    public String toString() {
        return "bluePrintTitle: " + bluePrintTitle + '\n' +
                "startDate: " + startDate.toString() + '\n' +
                "associatedLgac: " + associatedLgac + '\n' +
                "state: " + state + '\n' +
                "coDirector: " + coDirector + '\n' +
                "duration: " + duration + '\n' +
                "modality: " + modality + '\n' +
                "student: " + student + '\n' +
                "description: " + description + '\n';
    }

    @Override
    public boolean equals(Object o) {
        boolean iguales = false;
        if (this.getClass() == o.getClass()) {
            BluePrint bluePrint = (BluePrint) o;
            if (this.getBluePrintTitle().equals(bluePrint.getBluePrintTitle()) &&
                this.getStartDate().equals(bluePrint.getStartDate()) &&
                this.getAssociatedLgac().equals(bluePrint.getAssociatedLgac()) &&
                this.getState().equals(bluePrint.getState()) &&
                this.getCoDirector().equals(bluePrint.getCoDirector()) &&
                this.getDuration() == bluePrint.getDuration() &&
                this.getModality().equals(bluePrint.getModality()) &&
                this.getStudent().equals(bluePrint.getStudent()) &&
                this.getDescription().equals(bluePrint.getDescription()) &&
                this.getDirector().equals(bluePrint.getDirector()) &&
                this.getReceptionWorkName().equals(bluePrint.getReceptionWorkName()) &&
                this.getRequirements().equals(bluePrint.getRequirements())) {
                iguales = true;
            }
        }
        return iguales;
    }
}
