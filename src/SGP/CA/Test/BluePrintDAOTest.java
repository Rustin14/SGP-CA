package SGP.CA.Test;

import SGP.CA.DataAccess.BluePrintDAO;
import SGP.CA.Domain.BluePrint;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BluePrintDAOTest {

    BluePrintDAO bluePrintDAO = new BluePrintDAO();

    @Test
    public void saveBluePrintTest() throws SQLException, ClassNotFoundException, ParseException {
        BluePrint bluePrint = new BluePrint();
        bluePrint.setAssociatedLgac("No");
        bluePrint.setBluePrintTitle("Test Title");
        bluePrint.setCoDirector("Test coDirector");
        bluePrint.setDuration(10);
        bluePrint.setDescription("Test description");
        bluePrint.setModality("Test");
        bluePrint.setState("Test");
        bluePrint.setStudent("Test");
        String testDateString = "11/01/2001";
        Date testDate = new SimpleDateFormat("dd/MM/yyyy").parse(testDateString);
        bluePrint.setStartDate(testDate);
        bluePrint.setDirector("Test director");
        bluePrint.setReceptionWorkName("Test receptionWorkName");
        bluePrint.setRequirements("Test requirements");

        int successfulSave = bluePrintDAO.saveBluePrint(bluePrint);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void searchBluePrintByTitle() throws SQLException, ClassNotFoundException{
        BluePrint bluePrint = bluePrintDAO.searchBluePrintByTitle("Test Title");

        Assert.assertEquals("Test Title", bluePrint.getBluePrintTitle());
    }

    @Test
    public void modifyBluePrint() throws SQLException, ClassNotFoundException, ParseException {
        BluePrint bluePrint = new BluePrint();
        bluePrint.setAssociatedLgac("No");
        bluePrint.setBluePrintTitle("Test Title2");
        bluePrint.setCoDirector("Test coDirector2");
        bluePrint.setDuration(20);
        bluePrint.setDescription("Test description2");
        bluePrint.setModality("Test2");
        bluePrint.setState("Test2");
        bluePrint.setStudent("Test2");
        String testDateString = "22/02/2002";
        Date testDate = new SimpleDateFormat("dd/MM/yyyy").parse(testDateString);
        bluePrint.setStartDate(testDate);
        bluePrint.setDirector("Test director2");
        bluePrint.setReceptionWorkName("Test receptionWorkName2");
        bluePrint.setRequirements("Test requirements2");

        int successfulUpdate = bluePrintDAO.modifyBluePrint(bluePrint, "Test Title2");
        Assert.assertEquals(1, successfulUpdate, 0);
    }

    @Test
    public void deleteBluePrintTest() throws SQLException, ClassNotFoundException{
        String bluePrintTitle = "Test Title3";

        int successfulDelete = bluePrintDAO.deleteBluePrint(bluePrintTitle);
        Assert.assertEquals(1,successfulDelete, 0);
    }

    @Test
    public void getAllBluePrintsTest() throws SQLException, ClassNotFoundException, ParseException{
        BluePrint bluePrint1 = new BluePrint();
        bluePrint1.setAssociatedLgac("No");
        bluePrint1.setBluePrintTitle("Test Title");
        bluePrint1.setCoDirector("Test coDirector");
        bluePrint1.setDuration(10);
        bluePrint1.setDescription("Test description");
        bluePrint1.setModality("Test");
        bluePrint1.setState("Test");
        bluePrint1.setStudent("Test");
        String testDateString = "11/01/2001";
        Date testDate = new SimpleDateFormat("dd/MM/yyyy").parse(testDateString);
        bluePrint1.setStartDate(testDate);
        bluePrint1.setDirector("Test director");
        bluePrint1.setReceptionWorkName("Test receptionWorkName");
        bluePrint1.setRequirements("Test requirements");

        BluePrint bluePrint2 = new BluePrint();
        bluePrint2.setAssociatedLgac("No");
        bluePrint2.setBluePrintTitle("Test Title2");
        bluePrint2.setCoDirector("Test coDirector2");
        bluePrint2.setDuration(20);
        bluePrint2.setDescription("Test description2");
        bluePrint2.setModality("Test2");
        bluePrint2.setState("Test2");
        bluePrint2.setStudent("Test2");
        String testDateString2 = "22/02/2002";
        Date testDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(testDateString2);
        bluePrint2.setStartDate(testDate2);
        bluePrint2.setDirector("Test director2");
        bluePrint2.setReceptionWorkName("Test receptionWorkName2");
        bluePrint2.setRequirements("Test requirements2");

        ArrayList<BluePrint> allBluePrints = bluePrintDAO.getAllBluePrints();
        int equalObjects = 0;
        if (bluePrint1.equals(allBluePrints.get(1))){
            equalObjects+=1;
        }
        if (bluePrint2.equals(allBluePrints.get(0))){
            equalObjects+=1;
        }
        Assert.assertEquals(2, equalObjects, 0);
    }
}