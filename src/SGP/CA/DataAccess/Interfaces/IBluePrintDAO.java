package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.BluePrint;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IBluePrintDAO {
    int saveBluePrint(BluePrint bluePrint) throws SQLException, ClassNotFoundException;
    BluePrint searchBluePrintByTitle(String bluePrintTitle) throws SQLException, ClassNotFoundException;
    int modifyBluePrint(BluePrint newBluePrint, String oldBluePrintTitle) throws SQLException, ClassNotFoundException;
    int deleteBluePrint(String bluePrintTitle) throws SQLException, ClassNotFoundException;
    ArrayList<BluePrint> getAllBluePrints() throws SQLException, ClassNotFoundException;
}
