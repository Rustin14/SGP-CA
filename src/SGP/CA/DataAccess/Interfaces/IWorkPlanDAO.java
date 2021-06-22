package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.WorkPlan;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IWorkPlanDAO {
    int saveWorkPlan(WorkPlan workPlan) throws SQLException;
    WorkPlan searchWorkPlanByWorkPlanKey (String workPlanKey) throws SQLException;
    int modifyWorkPlan(WorkPlan newWorkPlan, String oldWorkPlanKey) throws SQLException;
    int deleteWorkPlan(String workPlanKey) throws SQLException;
    ArrayList<WorkPlan> getAllWorkPlans () throws SQLException;
}
