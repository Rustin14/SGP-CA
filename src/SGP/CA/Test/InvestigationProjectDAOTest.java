package SGP.CA.Test;

import SGP.CA.Domain.InvestigationProject;
import SGP.CA.DataAccess.InvestigationProjectDAO;
import org.junit.Test;
import org.junit.Assert;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvestigationProjectDAOTest {

    InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();

    @Test
    public void saveInvestigationProjectTest() throws SQLException, ClassNotFoundException, ParseException {
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setProjectTitle("Test Title");
        investigationProject.setAssociatedLgac("No");
        investigationProject.setParticipants("Test participants");
        String testStartDateString = "10/01/2002";
        Date testStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(testStartDateString);
        investigationProject.setStartDate(testStartDate);
        String testEndingDateString = "10/01/2002";
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
}
