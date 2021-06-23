package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Event;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IEventDAO {

    public int saveEvent(Event event) throws SQLException;
    public Event searchEventByEventID (int idEvent) throws SQLException;
    public int modifyEvent (Event event) throws SQLException;
    public ArrayList<Event> getAllEvent () throws SQLException;
    public int deleteEvent(int idEvent) throws SQLException;
}
