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
        workPlan.setWorkPlanKey("Test key4");
        workPlan.setObjective("Test objective4");
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
        WorkPlan workPlan = workPlanDAO.searchWorkPlanByWorkPlanKey("Test key3");

        Assert.assertEquals("Test key3",workPlan.getWorkPlanKey());
    }

    @Test
    public void modifyWorkPlanTest() throws SQLException, ParseException {
        WorkPlan workPlan = new WorkPlan();
        workPlan.setWorkPlanKey("Test key5");
        workPlan.setObjective("Test objective5");
        String testStartDateString = "31/03/2003";
        Date testStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString);
        workPlan.setStartDate(testStartDate);
        String testEndingDateString = "30/03/2013";
        Date testEndingDate = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString);
        workPlan.setEndingDate(testEndingDate);

        int successfulSave = workPlanDAO.modifyWorkPlan(workPlan, "Test key4");
        Assert.assertEquals(1 , successfulSave, 0);
    }

    @Test
    public void deleteWorkPlanTest() throws SQLException {
        String workPlanKey = "Test key5";

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
        workPlan2.setWorkPlanKey("Test key3");
        workPlan2.setObjective("Test objective3");
        String testStartDateString2 = "31/03/2003";
        Date testStartDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString2);
        workPlan2.setStartDate(testStartDate2);
        String testEndingDateString2 = "30/03/2013";
        Date testEndingDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString2);
        workPlan2.setEndingDate(testEndingDate2);

        WorkPlan workPlan3 = new WorkPlan();
        workPlan3.setWorkPlanKey("WK_01_21");
        workPlan3.setObjective("Probar el SGPCA");
        String testStartDateString3 = "22/06/2021";
        Date testStartDate3 = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString3);
        workPlan3.setStartDate(testStartDate3);
        String testEndingDateString3 = "12/06/2022";
        Date testEndingDate3 = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString3);
        workPlan3.setEndingDate(testEndingDate3);

        WorkPlan workPlan4 = new WorkPlan();
        workPlan4.setWorkPlanKey("WK_01_21");
        workPlan4.setObjective("Objetivo de prueba");
        String testStartDateString4 = "22/06/2021";
        Date testStartDate4 = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString4);
        workPlan4.setStartDate(testStartDate4);
        String testEndingDateString4 = "12/06/2022";
        Date testEndingDate4 = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString4);
        workPlan4.setEndingDate(testEndingDate4);

        WorkPlan workPlan5 = new WorkPlan();
        workPlan5.setWorkPlanKey("Test key4");
        workPlan5.setObjective("Test objective4");
        String testStartDateString5 = "22/02/2002";
        Date testStartDate5 = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString5);
        workPlan5.setStartDate(testStartDate5);
        String testEndingDateString5 = "22/02/2022";
        Date testEndingDate5 = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString5);
        workPlan5.setEndingDate(testEndingDate5);

        ArrayList<WorkPlan> allWorkPlans = workPlanDAO.getAllWorkPlans();
        int equalObjects = 0;
        if (workPlan1.equals(allWorkPlans.get(0))) {
            equalObjects+=1;
        }
        if (workPlan2.equals(allWorkPlans.get(1))) {
            equalObjects+=1;
        }
        if (workPlan3.equals(allWorkPlans.get(2))) {
            equalObjects+=1;
        }
        if (workPlan4.equals(allWorkPlans.get(3))) {
            equalObjects+=1;
        }
        if (workPlan5.equals(allWorkPlans.get(4))) {
            equalObjects+=1;
        }
        Assert.assertEquals(5, equalObjects, 0);
    }
}
