package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.BluePrint;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IBluePrintDAO {
    int saveBluePrint(BluePrint bluePrint) throws SQLException;
    BluePrint searchBluePrintByTitle(String bluePrintTitle) throws SQLException;
    int modifyBluePrint(BluePrint newBluePrint, String oldBluePrintTitle) throws SQLException;
    int deleteBluePrint(String bluePrintTitle) throws SQLException;
    ArrayList<BluePrint> getAllBluePrints() throws SQLException;
}
