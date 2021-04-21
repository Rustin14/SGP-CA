package SGP.CA.Test;

import SGP.CA.Domain.WorkPlan;
import SGP.CA.DataAccess.WorkPlanDAO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkPlanDAOTest {

    WorkPlanDAO workPlanDAO = new WorkPlanDAO();

    @Test
    public void saveWorkPlanTest() throws SQLException, ClassNotFoundException, ParseException {
        WorkPlan workPlan = new WorkPlan();
        workPlan.setWorkPlanKey("Test key");
        workPlan.setObjective("Test objective");
        String testStartDateString = "10/01/2001";
        Date testStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString);
        workPlan.setStartDate(testStartDate);
        String testEndingDateString = "10/01/2001";
        Date testEndingDate = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString);
        workPlan.setEndingDate(testEndingDate);

        int successfulSave = workPlanDAO.saveWorkPlan(workPlan);
        Assert.assertEquals(1 , successfulSave, 0);
    }

    @Test
    public void searchWorkPlanByWorkPlanKeyTest () throws SQLException, ClassNotFoundException {
        WorkPlan workPlan = workPlanDAO.searchWorkPlanByWorkPlanKey("Test key");

        Assert.assertEquals("Test key",workPlan.getWorkPlanKey());
    }

    @Test
    public void modifyWorkPlan() throws SQLException, ClassNotFoundException, ParseException {
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
    public void deleteWorkPlanTest() throws SQLException, ClassNotFoundException{
        String workPlanKey = "Test key3";

        int successfulDelete = workPlanDAO.deleteWorkPlan(workPlanKey);
        Assert.assertEquals(1,successfulDelete, 0);
    }
}
