package SGP.CA.DataAccess.Interfaces;

import java.sql.SQLException;

public interface IResponsibleDAO {
    public int saveResponsible(int idMember) throws SQLException;
}
