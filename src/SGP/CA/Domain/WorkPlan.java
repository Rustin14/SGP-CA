package SGP.CA.Domain;

import java.util.Date;

public class WorkPlan {

    private String workPlanKey;
    private Date startDate;
    private Date endingDate;
    private String objective;

    public WorkPlan(){
        this.workPlanKey = "";
        this.startDate = new Date();
        this.endingDate = new Date();
        this.objective = "";
    }

    public WorkPlan(String workPlanKey, Date startDate, Date endingDate, String objective){
        this.workPlanKey = workPlanKey;
        this.startDate = startDate;
        this.endingDate = endingDate;
        this.objective = objective;
    }

    public void setWorkPlanKey(String workPlanKey) {
        this.workPlanKey = workPlanKey;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getWorkPlanKey() {
        return workPlanKey;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public String getObjective() {
        return objective;
    }

    @Override
    public String toString() {
        return "workPlanKey: " + workPlanKey + '\n' +
                "startDate: " + startDate + '\n' +
                "endingDate: " + endingDate + '\n' +
                "objective: " + objective + '\n';
    }
}
