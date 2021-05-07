package SGP.CA.DataAccess.Interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import SGP.CA.Domain.*;

public interface ILGACDAO {
    
    public int saveLGAC (LGAC lgac) throws SQLException;
    public LGAC searchLGACbyLineName (String lineName) throws SQLException;
    public LGAC searchLGACbyID (int idLGAC) throws SQLException;
    public ArrayList<LGAC> getAllLines () throws SQLException;
    
}
