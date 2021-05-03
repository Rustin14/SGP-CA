package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.WorkPlan;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IWorkPlanDAO {
    int saveWorkPlan(WorkPlan workPlan) throws SQLException, ClassNotFoundException;
    WorkPlan searchWorkPlanByWorkPlanKey (String workPlanKey) throws SQLException, ClassNotFoundException;
    int modifyWorkPlan(WorkPlan newWorkPlan, String oldWorkPlanKey) throws SQLException, ClassNotFoundException;
    int deleteWorkPlan(String workPlanKey) throws SQLException, ClassNotFoundException;
    ArrayList<WorkPlan> getAllWorkPlans () throws SQLException, ClassNotFoundException;
}
