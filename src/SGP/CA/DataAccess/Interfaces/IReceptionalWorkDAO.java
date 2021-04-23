package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.ReceptionalWork;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IReceptionalWorkDAO {

    public int saveReceptionalWork (ReceptionalWork receptionalWork) throws SQLException;
    public ReceptionalWork searchWorkByName (String workName) throws SQLException;
    public ArrayList<ReceptionalWork> getAllWorks () throws SQLException;

}
