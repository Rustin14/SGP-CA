package SGP.CA.DataAccess;

import SGP.CA.DataAccess.Interfaces.IResponsibleDAO;
import SGP.CA.Domain.Responsible;
import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ResponsibleDAO implements IResponsibleDAO {

    @Override
    public void saveResponsible(Responsible responsible) throws SQLException, ClassNotFoundException {
        throw new OperationNotSupportedException();
    }

    @Override
    public Responsible searchResponsibleByMemberID(int memberID) throws SQLException, ClassNotFoundException {
        throw new OperationNotSupportedException();

    }

    @Override
    public ArrayList<Responsible> getAllResponsibles() throws SQLException, ClassNotFoundException {
        throw new OperationNotSupportedException();
    }
}
