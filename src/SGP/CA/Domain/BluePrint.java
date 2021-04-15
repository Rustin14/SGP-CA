package SGP.CA.Domain;

import java.util.Date;

public class BluePrint {

    private String bluePrintTitle;
    private Date startDate;
    private String associatedLgac;
    private String state;
    private String coDirector;
    private int duration;
    private String modality;
    private String student;
    private String description;

    public BluePrint(){
        this.bluePrintTitle = "";
        this.startDate = new Date();
        this.associatedLgac = "";
        this.state = "";
        this.coDirector = "";
        this.duration = 0;
        this.modality = "";
        this.student = "";
        this.description = "";
    }

    public BluePrint(String bluePrintTitle, Date startDate, String associatedLgac, String state, String coDirector, int duration, String modality, String student, String description) {
        this.bluePrintTitle = bluePrintTitle;
        this.startDate = startDate;
        this.associatedLgac = associatedLgac;
        this.state = state;
        this.coDirector = coDirector;
        this.duration = duration;
        this.modality = modality;
        this.student = student;
        this.description = description;
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
}
