package SGP.CA.Test;

import SGP.CA.Domain.WorkPlan;
import SGP.CA.DataAccess.WorkPlanDAO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkPlanDAOTest {

    WorkPlanDAO workPlanDAO = new WorkPlanDAO();

    @Test
    public void saveWorkPlanTest() throws SQLException, ParseException {
        WorkPlan workPlan = new WorkPlan();
        workPlan.setWorkPlanKey("Test key2");
        workPlan.setObjective("Test objective2");
        String testStartDateString = "22/02/2002";
        Date testStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString);
        workPlan.setStartDate(testStartDate);
        String testEndingDateString = "22/02/2022";
        Date testEndingDate = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString);
        workPlan.setEndingDate(testEndingDate);

        int successfulSave = workPlanDAO.saveWorkPlan(workPlan);
        Assert.assertEquals(1 , successfulSave, 0);
    }

    @Test
    public void searchWorkPlanByWorkPlanKeyTest () throws SQLException {
        WorkPlan workPlan = workPlanDAO.searchWorkPlanByWorkPlanKey("Test key");

        Assert.assertEquals("Test key",workPlan.getWorkPlanKey());
    }

    @Test
    public void modifyWorkPlan() throws SQLException, ParseException {
        WorkPlan workPlan = new WorkPlan();
        workPlan.setWorkPlanKey("Test key3");
        workPlan.setObjective("Test objective3");
        String testStartDateString = "31/03/2003";
        Date testStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString);
        workPlan.setStartDate(testStartDate);
        String testEndingDateString = "30/03/2013";
        Date testEndingDate = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString);
        workPlan.setEndingDate(testEndingDate);

        int successfulSave = workPlanDAO.modifyWorkPlan(workPlan, "Test key2");
        Assert.assertEquals(1 , successfulSave, 0);
    }

    @Test
    public void deleteWorkPlanTest() throws SQLException {
        String workPlanKey = "Test key3";

        int successfulDelete = workPlanDAO.deleteWorkPlan(workPlanKey);
        Assert.assertEquals(1,successfulDelete, 0);
    }

    @Test
    public void getAllWorkPlansTest () throws SQLException, ParseException {
        WorkPlan workPlan1 = new WorkPlan();
        workPlan1.setWorkPlanKey("Test key");
        workPlan1.setObjective("Test objective");
        String testStartDateString = "10/01/2001";
        Date testStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString);
        workPlan1.setStartDate(testStartDate);
        String testEndingDateString = "10/01/2001";
        Date testEndingDate = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString);
        workPlan1.setEndingDate(testEndingDate);

        WorkPlan workPlan2 = new WorkPlan();
        workPlan2.setWorkPlanKey("Test key2");
        workPlan2.setObjective("Test objective2");
        String testStartDateString2 = "22/02/2002";
        Date testStartDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString2);
        workPlan2.setStartDate(testStartDate2);
        String testEndingDateString2 = "22/02/2022";
        Date testEndingDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString2);
        workPlan2.setEndingDate(testEndingDate2);

        ArrayList<WorkPlan> allWorkPlans = workPlanDAO.getAllWorkPlans();
        int equalObjects = 0;
        if (workPlan1.equals(allWorkPlans.get(0))){
            equalObjects+=1;
        }
        if (workPlan2.equals(allWorkPlans.get(1))){
            equalObjects+=1;
        }
        Assert.assertEquals(2, equalObjects, 0);
    }
}
