package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IEventDAO;
import SGP.CA.Domain.Event;

import java.sql.*;
import java.util.ArrayList;

public class EventDAO implements IEventDAO {


    @Override
    public int saveEvent(Event event) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO event (eventName, eventDate, eventPlace) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, event.getEventName());
        java.sql.Date sqlEventDate = new java.sql.Date(event.getEventDate().getTime());
        statement.setDate(2,  sqlEventDate);
        statement.setString(3, event.getEventPlace());
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

    @Override
    public Event searchEventByEventID(int idEvent) throws SQLException {
        Event event = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * from event WHERE idEvent = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idEvent);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            event = new Event();
            event.setIdEvent(results.getInt("idEvent"));
            event.setEventName(results.getString("eventName"));
            event.setEventType(results.getString("eventType"));
            java.util.Date registrationDate = new java.util.Date(results.getDate("registrationDate").getTime());
            event.setRegistrationDate(registrationDate);
            java.util.Date eventDate = new java.util.Date(results.getDate("eventDate").getTime());
            event.setEventDate(eventDate);
            event.setEventPlace(results.getString("eventPlace"));
            event.setIdMember(results.getInt("idMember"));
        }
        return event;
    }

    @Override
    public ArrayList<Event> getAllEvent() throws SQLException {
        ArrayList<Event> getAllEvent = new ArrayList<>();
        Event event = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * from event";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            event = new Event();
            event.setIdEvent(results.getInt("idEvent"));
            event.setEventName(results.getString("eventName"));
            event.setEventType(results.getString("eventType"));
            java.util.Date registrationDate = new java.util.Date(results.getDate("registrationDate").getTime());
            event.setRegistrationDate(registrationDate);
            java.util.Date eventDate = new java.util.Date(results.getDate("eventDate").getTime());
            event.setEventDate(eventDate);
            event.setEventPlace(results.getString("eventPlace"));
            event.setIdMember(results.getInt("idMember"));
            event.setActive(results.getInt("active"));
            getAllEvent.add(event);
        }
        return getAllEvent;
    }
}
