package SGP.CA.Test;

import SGP.CA.Domain.Objective;
import SGP.CA.DataAccess.ObjectiveDAO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class ObjectiveDAOTest {

    ObjectiveDAO objectiveDAO = new ObjectiveDAO();

    @Test
    public void saveObjectiveTest() throws SQLException, ClassNotFoundException{
        Objective objective = new Objective();
        objective.setObjectiveTitle("Test objective");
        objective.setDescription("Test description");
        objective.setStrategy("Test strategy");

        int successfulSave = objectiveDAO.saveObjective(objective);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void searchObjectiveByTitleTest() throws SQLException, ClassNotFoundException {
        Objective objective = objectiveDAO.searchObjectiveByTitle("Test objective");

        Assert.assertEquals("Test objective",objective.getObjectiveTitle());
    }

    @Test
    public void modifyObjective () throws SQLException, ClassNotFoundException {
        Objective objective = new Objective();
        objective.setObjectiveTitle("Test objective3");
        objective.setDescription("Test description3");
        objective.setStrategy("Test strategy3");

        int successfulSave = objectiveDAO.modifyObjective(objective, "Test objective2");
        Assert.assertEquals(1, successfulSave, 0);
    }
}
