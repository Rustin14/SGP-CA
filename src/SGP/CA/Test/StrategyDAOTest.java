package SGP.CA.Test;

import SGP.CA.DataAccess.StrategyDAO;
import SGP.CA.Domain.Strategy;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class StrategyDAOTest {

    StrategyDAO strategyDAO = new StrategyDAO();

    @Test
    public void saveStrategyTest() throws SQLException, ClassNotFoundException{
        Strategy strategy = new Strategy();
        strategy.setStrategy("Test Strategy");
        strategy.setGoal("Test goal");
        strategy.setAction("Test action");
        strategy.setNumber(1);
        strategy.setResult("Test result");

        int successfulSave = strategyDAO.saveStrategy(strategy);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void searchStrategyByStrategyTest () throws SQLException,ClassNotFoundException{
        Strategy strategy = strategyDAO.searchStrategyByStrategy("Test Strategy");

        Assert.assertEquals("Test Strategy", strategy.getStrategy());
    }

    @Test
    public void modifyStrategy() throws SQLException,ClassNotFoundException {
        Strategy strategy = new Strategy();
        strategy.setStrategy("Test Strategy3");
        strategy.setGoal("Test goal3");
        strategy.setAction("Test action3");
        strategy.setNumber(3);
        strategy.setResult("Test result3");

        int successfulSave = strategyDAO.modifyStrategy(strategy, "Test Strategy2");
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void deleteStrategyTest() throws SQLException, ClassNotFoundException{
        String strategyTitle = "Test Strategy3";

        int successfulDelete = strategyDAO.deleteStrategy(strategyTitle);
        Assert.assertEquals(1,successfulDelete, 0);
    }
}
