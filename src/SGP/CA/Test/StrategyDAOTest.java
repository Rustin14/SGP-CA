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
        Strategy strategy = strategyDAO.searchStrategyByStrategy("Estrategia 5");

        Assert.assertEquals("Estrategia 5", strategy.getStrategy());
    }

    @Test
    public void modifyStrategyTest() throws SQLException {
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
        strategy2.setStrategy("Estrategia 1");
        strategy2.setGoal("Meta 1");
        strategy2.setAction("Accion 1");
        strategy2.setNumber(1);
        strategy2.setResult("Resultado 1");

        Strategy strategy3 = new Strategy();
        strategy3.setStrategy("Test Strategy2");
        strategy3.setGoal("Test goal2");
        strategy3.setAction("Test action2");
        strategy3.setNumber(2);
        strategy3.setResult("Test result2");

        Strategy strategy4 = new Strategy();
        strategy4.setStrategy("Estrategia 2");
        strategy4.setGoal("Meta 2");
        strategy4.setAction("Accion 2");
        strategy4.setNumber(2);
        strategy4.setResult("Resultado 2");

        Strategy strategy5 = new Strategy();
        strategy5.setStrategy("Test strategy3");
        strategy5.setGoal("Test goal3");
        strategy5.setAction("Test action3");
        strategy5.setNumber(3);
        strategy5.setResult("Test Result3");

        Strategy strategy6 = new Strategy();
        strategy6.setStrategy("Estrategia 6");
        strategy6.setGoal("Meta 6");
        strategy6.setAction("Accion 6");
        strategy6.setNumber(6);
        strategy6.setResult("Resultado 6");

        Strategy strategy7 = new Strategy();
        strategy7.setStrategy("Estrategia 7");
        strategy7.setGoal("Meta 7");
        strategy7.setAction("Accion 7");
        strategy7.setNumber(7);
        strategy7.setResult("Resultado 7");

        Strategy strategy8 = new Strategy();
        strategy8.setStrategy("Estrategia 3");
        strategy8.setGoal("Meta 3");
        strategy8.setAction("Accion 3");
        strategy8.setNumber(3);
        strategy8.setResult("Resultado 3");

        Strategy strategy9 = new Strategy();
        strategy9.setStrategy("Estrategia 5");
        strategy9.setGoal("Meta 5");
        strategy9.setAction("Accion 5");
        strategy9.setNumber(5);
        strategy9.setResult("Resultado 5");

        Strategy strategy10 = new Strategy();
        strategy10.setStrategy("Estrategia 4");
        strategy10.setGoal("Meta 4");
        strategy10.setAction("Accion 4");
        strategy10.setNumber(4);
        strategy10.setResult("Resultado 4");

        Strategy strategy11 = new Strategy();
        strategy11.setStrategy("Test Strategy4");
        strategy11.setGoal("Test goal4");
        strategy11.setAction("Test action4");
        strategy11.setNumber(4);
        strategy11.setResult("Test result4");

        ArrayList<Strategy> allStrategies = strategyDAO.getAllStrategy();
        int equalObjects = 0;
        if (allStrategies.get(0).equals(strategy1)) {
            equalObjects+=1;
        }
        if (allStrategies.get(1).equals(strategy2)) {
            equalObjects+=1;
        }
        if (allStrategies.get(2).equals(strategy3)) {
            equalObjects+=1;
        }
        if (allStrategies.get(3).equals(strategy4)) {
            equalObjects+=1;
        }
        if (allStrategies.get(4).equals(strategy5)) {
            equalObjects+=1;
        }
        if (allStrategies.get(5).equals(strategy6)) {
            equalObjects+=1;
        }
        if (allStrategies.get(6).equals(strategy7)) {
            equalObjects+=1;
        }
        if (allStrategies.get(7).equals(strategy8)) {
            equalObjects+=1;
        }
        if (allStrategies.get(8).equals(strategy9)) {
            equalObjects+=1;
        }
        if (allStrategies.get(9).equals(strategy10)) {
            equalObjects+=1;
        }
        if (allStrategies.get(10).equals(strategy11)) {
            equalObjects+=1;
        }
        Assert.assertEquals(11, equalObjects,0);
    }
}
