package SGP.CA.Test;

import SGP.CA.DataAccess.EventDAO;
import SGP.CA.Domain.Event;
import org.junit.Assert;
import org.junit.Test;
import java.sql.Date;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventDAOTest {

    EventDAO eventDAO = new EventDAO();

    @Test
    public void saveEventTest () throws SQLException, ClassNotFoundException, ParseException {
        Event event = new Event();
        event.setEventName("Test");
        event.setEventPlace("Test");
        String testDateString = "01/01/2000";
        java.util.Date testDate = new SimpleDateFormat("dd/MM/yyyy").parse(testDateString);
        event.setEventDate(testDate);
        event.setResponsableName("Test");

        int successfulSave = eventDAO.saveEvent(event);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void searchEventByEventID () throws SQLException, ClassNotFoundException {
        Event event = eventDAO.searchEventByEventID(1);

        Assert.assertEquals(1,event.getIdEvent(),0);
    }

    @Test
    public void getAllEventTest() throws SQLException, ClassNotFoundException {
        ArrayList<Event> allEvents = eventDAO.getAllEvent();

        Assert.assertEquals("Examen Recepcional: Marco Rodríguez", allEvents.get(0).getEventName());
    }
}
