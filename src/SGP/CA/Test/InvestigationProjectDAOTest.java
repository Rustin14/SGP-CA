package SGP.CA.Test;

import SGP.CA.Domain.InvestigationProject;
import SGP.CA.DataAccess.InvestigationProjectDAO;
import org.junit.Test;
import org.junit.Assert;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvestigationProjectDAOTest {

    InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();

    @Test
    public void saveInvestigationProjectTest() throws SQLException, ClassNotFoundException, ParseException {
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setProjectTitle("Test Title2");
        investigationProject.setAssociatedLgac("No");
        investigationProject.setParticipants("Test participants2");
        String testStartDateString = "22/02/2002";
        Date testStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString);
        investigationProject.setStartDate(testStartDate);
        String testEndingDateString = "22/02/2022";
        Date testEndingtDate = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString);
        investigationProject.setEstimatedEndDate(testEndingtDate);

        int successfulSave = investigationProjectDAO.saveInvestigationProject(investigationProject);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void searchInvestigationProjectByTitle()throws SQLException, ClassNotFoundException{
        InvestigationProject investigationProject = investigationProjectDAO.searchInvestigationProjectByTitle("Test Title");

        Assert.assertEquals("Test Title", investigationProject.getProjectTitle());
    }

    @Test
    public void modifyInvestigationProject () throws SQLException, ClassNotFoundException, ParseException {
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setProjectTitle("Test Title3");
        investigationProject.setAssociatedLgac("No");
        investigationProject.setParticipants("Test participants3");
        String testStartDateString = "31/03/2003";
        Date testStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString);
        investigationProject.setStartDate(testStartDate);
        String testEndingDateString = "31/09/2003";
        Date testEndingtDate = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString);
        investigationProject.setEstimatedEndDate(testEndingtDate);

        int successfulSave = investigationProjectDAO.modifyInvestigationProject(investigationProject, "Test Title2");
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void deleteInvestigationProjectTest() throws SQLException, ClassNotFoundException{
        String investigationProjectTitle = "Test Title3";

        int successfulDelete = investigationProjectDAO.deleteInvestigationProject(investigationProjectTitle);
        Assert.assertEquals(1,successfulDelete, 0);
    }

    @Test
    public void getAllInvestigationProjectsTest () throws SQLException, ClassNotFoundException, ParseException{
        InvestigationProject investigationProject1 = new InvestigationProject();
        investigationProject1.setProjectTitle("Test Title2");
        investigationProject1.setAssociatedLgac("No");
        investigationProject1.setParticipants("Test participants2");
        String testStartDateString = "22/02/2002";
        Date testStartDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString);
        investigationProject1.setStartDate(testStartDate1);
        String testEndingDateString = "22/02/2022";
        Date testEndingDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString);
        investigationProject1.setEstimatedEndDate(testEndingDate1);

        InvestigationProject investigationProject2 = new InvestigationProject();
        investigationProject2.setProjectTitle("Test Title");
        investigationProject2.setAssociatedLgac("No");
        investigationProject2.setParticipants("Test participants");
        String testStartDateString2 = "10/01/2001";
        Date testStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString2);
        investigationProject2.setStartDate(testStartDate);
        String testEndingDateString2 = "10/01/2001";
        Date testEndingDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(testEndingDateString2);
        investigationProject2.setEstimatedEndDate(testEndingDate2);

        ArrayList<InvestigationProject> allInvestigationProjects = investigationProjectDAO.getAllInvestigationProjects();
        int equalObjects = 0;
        if (investigationProject2.equals(allInvestigationProjects.get(0))){
            equalObjects+=1;
        }
        if (investigationProject1.equals(allInvestigationProjects.get(1))){
            equalObjects+=1;
        }
        Assert.assertEquals(2,equalObjects,0);
    }
}
