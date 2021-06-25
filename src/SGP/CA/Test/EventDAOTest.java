package SGP.CA.Test;

import SGP.CA.DataAccess.EventDAO;
import SGP.CA.Domain.Event;
import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class EventDAOTest {

    EventDAO eventDAO = new EventDAO();

    @Test
    public void saveEventTest () throws SQLException {
        Event event = new Event();
        event.setEventName("Examen Recepcional");
        event.setEventPlace("Auditorio de la FEI");
        LocalDate registrationDate = LocalDate.of(2021, 6, 23);
        java.util.Date utilRegistrationDate = new Date();
        utilRegistrationDate.from(registrationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        event.setRegistrationDate(utilRegistrationDate);
        LocalDate eventDate = LocalDate.of(2021, 6, 30);
        java.util.Date utilEventDate = new Date();
        utilEventDate.from(eventDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        event.setEventDate(utilEventDate);
        event.setResponsableName("Carlos Gabriel Flores Lira");
        event.setEventHour("13:30");
        event.setIdMember(1);
        int successfulSave = eventDAO.saveEvent(event);

        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void modifyEventTest () throws SQLException {
        Event event = new Event();
        event.setIdEvent(20);
        event.setEventName("Examen Recepcional");
        event.setEventPlace("Auditorio de la FEI");
        LocalDate registrationDate = LocalDate.of(2021, 6, 23);
        java.util.Date utilRegistrationDate = new Date();
        utilRegistrationDate.from(registrationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        event.setRegistrationDate(utilRegistrationDate);

        LocalDate eventDate = LocalDate.of(2021, 6, 30);
        java.util.Date utilEventDate = new Date();
        utilEventDate.from(eventDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        event.setEventDate(utilEventDate);

        event.setResponsableName("Carlos Gabriel Flores Lira");
        event.setEventHour("13:45");
        int successfulSave = eventDAO.modifyEvent(event);

        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void deleteEventTest () throws SQLException {
        int successfulTest = eventDAO.deleteEvent(20);

        Assert.assertEquals(1, successfulTest, 0);
    }

    @Test
    public void searchEventByEventID () throws SQLException {
        Event event = eventDAO.searchEventByEventID(1);

        Assert.assertEquals(1, event.getIdEvent(),0);
    }

    @Test
    public void getAllEventTest() throws SQLException {
        ArrayList<Event> allEvents = eventDAO.getAllEvent();

        Assert.assertEquals("Examen Recepcional: Kenya Contreras", allEvents.get(0).getEventName());
    }
}
