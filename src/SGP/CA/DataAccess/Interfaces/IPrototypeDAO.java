package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Prototype;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IPrototypeDAO {

    public int savePrototype (Prototype prototype) throws SQLException;
    public Prototype searchPrototypeByName (String prototypeName) throws SQLException;
    public ArrayList<Prototype> getAllPrototypes () throws SQLException;

}
