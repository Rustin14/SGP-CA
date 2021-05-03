package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Strategy;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IStrategyDAO {
    int saveStrategy(Strategy strategy) throws SQLException, ClassNotFoundException;
    Strategy searchStrategyByStrategy(String strategyTitle) throws SQLException,ClassNotFoundException;
    int modifyStrategy(Strategy newStrategy, String oldStrategy) throws SQLException,ClassNotFoundException;
    int deleteStrategy(String strategyTitle) throws SQLException,ClassNotFoundException;
    ArrayList<Strategy> getAllStrategy () throws SQLException,ClassNotFoundException;
}
