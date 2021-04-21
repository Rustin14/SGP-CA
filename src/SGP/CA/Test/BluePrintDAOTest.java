package SGP.CA.Test;

import SGP.CA.DataAccess.BluePrintDAO;
import SGP.CA.Domain.BluePrint;
import org.junit.Test;
import org.junit.Assert;
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
        bluePrint.setDuration(30);
        bluePrint.setDescription("Test description");
        bluePrint.setModality("Test");
        bluePrint.setState("Test");
        bluePrint.setStudent("Test");
        String testDateString = "10/01/2001";
        Date testDate = new SimpleDateFormat("dd/MM/yyyy").parse(testDateString);
        bluePrint.setStartDate(testDate);

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
        bluePrint.setBluePrintTitle("Test Title3");
        bluePrint.setCoDirector("Test coDirector3");
        bluePrint.setDuration(30);
        bluePrint.setDescription("Test description3");
        bluePrint.setModality("Test3");
        bluePrint.setState("Test3");
        bluePrint.setStudent("Test3");
        String testDateString = "31/03/2003";
        Date testDate = new SimpleDateFormat("dd/MM/yyyy").parse(testDateString);
        bluePrint.setStartDate(testDate);

        int successfulUpdate = bluePrintDAO.modifyBluePrint(bluePrint, "Test Title2");
        Assert.assertEquals(1, successfulUpdate, 0);
    }

    @Test
    public void deleteBluePrintTest() throws SQLException, ClassNotFoundException{
        String bluePrintTitle = "Test Title3";

        int successfulDelete = bluePrintDAO.deleteBluePrint(bluePrintTitle);
        Assert.assertEquals(1,successfulDelete, 0);
    }
}
