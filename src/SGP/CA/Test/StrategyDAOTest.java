package SGP.CA.Test;

import SGP.CA.DataAccess.StrategyDAO;
import SGP.CA.Domain.Strategy;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class StrategyDAOTest {

    StrategyDAO strategyDAO = new StrategyDAO();

    @Test
    public void saveStrategyTest() throws SQLException{
        Strategy strategy = new Strategy();
        strategy.setStrategy("Test Strategy4");
        strategy.setGoal("Test goal4");
        strategy.setAction("Test action4");
        strategy.setNumber(4);
        strategy.setResult("Test result4");

        int successfulSave = strategyDAO.saveStrategy(strategy);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void searchStrategyByStrategyTest () throws SQLException {
        Strategy strategy = strategyDAO.searchStrategyByStrategy("Test Strategy4");

        Assert.assertEquals("Test Strategy4", strategy.getStrategy());
    }

    @Test
    public void modifyStrategy() throws SQLException {
        Strategy strategy = new Strategy();
        strategy.setStrategy("Test Strategy5");
        strategy.setGoal("Test goal5");
        strategy.setAction("Test action5");
        strategy.setNumber(5);
        strategy.setResult("Test result5");

        int successfulSave = strategyDAO.modifyStrategy(strategy, "Test Strategy4");
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void deleteStrategyTest() throws SQLException {
        String strategyTitle = "Test Strategy5";

        int successfulDelete = strategyDAO.deleteStrategy(strategyTitle);
        Assert.assertEquals(1,successfulDelete, 0);
    }

    @Test
    public void getAllStrategyTest () throws SQLException {
        Strategy strategy1 = new Strategy();
        strategy1.setStrategy("Test Strategy");
        strategy1.setGoal("Test goal");
        strategy1.setAction("Test action");
        strategy1.setNumber(1);
        strategy1.setResult("Test result");

        Strategy strategy2 = new Strategy();
        strategy2.setStrategy("Test Strategy2");
        strategy2.setGoal("Test goal2");
        strategy2.setAction("Test action2");
        strategy2.setNumber(2);
        strategy2.setResult("Test result2");

        ArrayList<Strategy> allStrategies = strategyDAO.getAllStrategy();
        int equalObjects = 0;
        if (allStrategies.get(0).equals(strategy1)){
            equalObjects+=1;
        }
        if (allStrategies.get(1).equals(strategy2)){
            equalObjects+=1;
        }
        Assert.assertEquals(2, equalObjects,0);
    }
}
