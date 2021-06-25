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
        String query = "INSERT INTO event (eventName, responsableName, registrationDate, eventPlace, eventDate, eventHour, idMember) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, event.getEventName());
        statement.setString(2, event.getResponsableName());
        java.sql.Date sqlRegistrationDate = new java.sql.Date(event.getRegistrationDate().getTime());
        statement.setDate(3, sqlRegistrationDate);
        java.sql.Date sqlEventDate = new java.sql.Date(event.getEventDate().getTime());
        statement.setString(4, event.getEventPlace());
        statement.setDate(5,  sqlEventDate);
        statement.setString(6, event.getEventHour());
        statement.setInt(7, event.getIdMember());
        int successfulUpdate = statement.executeUpdate();
        databaseConnection.closeConnection();
        return successfulUpdate;
    }

    public int modifyEvent(Event event) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "UPDATE event SET eventName = ?,  responsableName = ?, registrationDate = ?, " +
                "eventPlace = ?, eventDate = ?, eventHour = ?, idMember = ? WHERE idEvent = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, event.getEventName());
        statement.setString(2, event.getResponsableName());
        java.sql.Date sqlRegistrationDate = new java.sql.Date(event.getRegistrationDate().getTime());
        statement.setDate(3, sqlRegistrationDate);
        java.sql.Date sqlEventDate = new java.sql.Date(event.getEventDate().getTime());
        statement.setString(4, event.getEventPlace());
        statement.setDate(5,  sqlEventDate);
        statement.setString(6, event.getEventHour());
        statement.setInt(7, event.getIdMember());
        statement.setInt(8, event.getIdEvent());
        int successfulUpdate = statement.executeUpdate();
        databaseConnection.closeConnection();
        return successfulUpdate;
    }

    @Override
    public int deleteEvent(int idEvent) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "UPDATE event SET active = 0 WHERE idEvent = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idEvent);
        int successfulUpdate = statement.executeUpdate();
        databaseConnection.closeConnection();
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
            java.util.Date registrationDate = new java.util.Date(results.getDate("registrationDate").getTime());
            event.setRegistrationDate(registrationDate);
            java.util.Date eventDate = new java.util.Date(results.getDate("eventDate").getTime());
            event.setEventDate(eventDate);
            event.setEventPlace(results.getString("eventPlace"));
            event.setEventHour(results.getString("eventHour"));
            event.setResponsableName(results.getString("responsableName"));
            event.setIdMember(results.getInt("idMember"));
            event.setActive(results.getInt("active"));
        }
        databaseConnection.closeConnection();
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
            java.util.Date registrationDate = new java.util.Date(results.getDate("registrationDate").getTime());
            event.setRegistrationDate(registrationDate);
            java.util.Date eventDate = new java.util.Date(results.getDate("eventDate").getTime());
            event.setEventDate(eventDate);
            event.setEventPlace(results.getString("eventPlace"));
            event.setEventHour(results.getString("eventHour"));
            event.setResponsableName(results.getString("responsableName"));
            event.setIdMember(results.getInt("idMember"));
            event.setActive(results.getInt("active"));
            getAllEvent.add(event);
        }
        databaseConnection.closeConnection();
        return getAllEvent;
    }
}
