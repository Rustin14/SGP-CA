package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Event;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IEventDAO {

    public int saveEvent(Event event) throws SQLException, ClassNotFoundException;
    public Event searchEventByEventID (int idEvent) throws SQLException, ClassNotFoundException;
    public ArrayList<Event> getAllEvent () throws SQLException, ClassNotFoundException;
}
