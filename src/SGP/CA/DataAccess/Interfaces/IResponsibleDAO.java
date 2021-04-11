package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Responsible;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IResponsibleDAO {
    public void saveResponsible(Responsible responsible) throws SQLException, ClassNotFoundException;
    public Responsible searchResponsibleByMemberID(int memberID) throws SQLException, ClassNotFoundException;
    public ArrayList<Responsible> getAllResponsibles() throws SQLException, ClassNotFoundException;
}
