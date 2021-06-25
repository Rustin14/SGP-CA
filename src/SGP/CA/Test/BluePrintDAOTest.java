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
    public void saveBluePrintTest() throws SQLException, ParseException {
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
    public void modifyBluePrintTest() throws SQLException, ParseException {
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

        int successfulUpdate = bluePrintDAO.modifyBluePrint(bluePrint, "Test Title");
        Assert.assertEquals(1, successfulUpdate, 0);
    }

    @Test
    public void deleteBluePrintTest() throws SQLException {
        String bluePrintTitle = "Test Title2";

        int successfulDelete = bluePrintDAO.deleteBluePrint(bluePrintTitle);
        Assert.assertEquals(1,successfulDelete, 0);
    }

    @Test
    public void searchBluePrintByTitleTest() throws SQLException {
        BluePrint bluePrint = bluePrintDAO.searchBluePrintByTitle("Proyecto de prueba tercero");

        Assert.assertEquals("Proyecto de prueba tercero", bluePrint.getBluePrintTitle());
    }

    @Test
    public void getAllBluePrintsTest() throws SQLException, ParseException {
        BluePrint bluePrint1 = new BluePrint();
        bluePrint1.setAssociatedLgac("Ninguna");
        bluePrint1.setBluePrintTitle("Proyecto de prueba tercero");
        bluePrint1.setCoDirector("Miriam Benitez");
        bluePrint1.setDuration(13);
        bluePrint1.setDescription("Alguien por favor ya detenga esto");
        bluePrint1.setModality("Nocturno");
        bluePrint1.setState("Casi terminado");
        bluePrint1.setStudent("Hector David Madrid Rivera");
        String testDateString = "25/06/2021";
        Date testDate = new SimpleDateFormat("dd/MM/yyyy").parse(testDateString);
        bluePrint1.setStartDate(testDate);
        bluePrint1.setDirector("Raul Hernandez");
        bluePrint1.setReceptionWorkName("Titulo de trabajo");
        bluePrint1.setRequirements("Ser feliz");

        BluePrint bluePrint2 = new BluePrint();
        bluePrint2.setAssociatedLgac("No");
        bluePrint2.setBluePrintTitle("Test Title");
        bluePrint2.setCoDirector("Test coDirector");
        bluePrint2.setDuration(10);
        bluePrint2.setDescription("Test description");
        bluePrint2.setModality("Test");
        bluePrint2.setState("Test");
        bluePrint2.setStudent("Test");
        String testDateString2 = "11/01/2001";
        Date testDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(testDateString2);
        bluePrint2.setStartDate(testDate2);
        bluePrint2.setDirector("Test director");
        bluePrint2.setReceptionWorkName("Test receptionWorkName");
        bluePrint2.setRequirements("Test requirements");

        ArrayList<BluePrint> allBluePrints = bluePrintDAO.getAllBluePrints();
        int equalObjects = 0;
        if (bluePrint1.equals(allBluePrints.get(0))) {
            equalObjects+=1;
        }
        if (bluePrint2.equals(allBluePrints.get(1))) {
            equalObjects+=1;
        }
        Assert.assertEquals(2, equalObjects, 0);
    }
}