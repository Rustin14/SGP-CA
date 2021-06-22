package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Strategy;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IStrategyDAO {
    int saveStrategy(Strategy strategy) throws SQLException;
    Strategy searchStrategyByStrategy(String strategyTitle) throws SQLException;
    int modifyStrategy(Strategy newStrategy, String oldStrategy) throws SQLException;
    int deleteStrategy(String strategyTitle) throws SQLException;
    ArrayList<Strategy> getAllStrategy () throws SQLException;
}
