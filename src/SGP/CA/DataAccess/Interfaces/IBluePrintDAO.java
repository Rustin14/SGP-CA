package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.BluePrint;
import java.sql.SQLException;

public interface IBluePrintDAO {
    int saveBluePrint(BluePrint bluePrint) throws SQLException, ClassNotFoundException;
    BluePrint searchBluePrintByTitle(String bluePrintTitle) throws SQLException, ClassNotFoundException;
    int modifyBluePrint(BluePrint newBluePrint, String oldBluePrintTitle) throws SQLException, ClassNotFoundException;
}
