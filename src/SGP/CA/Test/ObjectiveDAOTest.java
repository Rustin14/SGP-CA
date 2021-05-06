package SGP.CA.Test;

import SGP.CA.Domain.Objective;
import SGP.CA.DataAccess.ObjectiveDAO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class ObjectiveDAOTest {

    ObjectiveDAO objectiveDAO = new ObjectiveDAO();

    @Test
    public void saveObjectiveTest() throws SQLException, ClassNotFoundException{
        Objective objective = new Objective();
        objective.setObjectiveTitle("Test objective2");
        objective.setDescription("Test description2");
        objective.setStrategy("Test strategy2");

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

    @Test
    public void deleteObjectiveTest() throws SQLException, ClassNotFoundException{
        String objectiveTitle = "Test objective3";

        int successfulDelete = objectiveDAO.deleteObjective(objectiveTitle);
        Assert.assertEquals(1,successfulDelete, 0);
    }

    @Test
    public void getAllObjectivesTest () throws SQLException, ClassNotFoundException{
        Objective objective1 = new Objective();
        objective1.setObjectiveTitle("Test objective");
        objective1.setDescription("Test description");
        objective1.setStrategy("Test strategy");

        Objective objective2 = new Objective();
        objective2.setObjectiveTitle("Test objective2");
        objective2.setDescription("Test description2");
        objective2.setStrategy("Test strategy2");

        ArrayList<Objective> allObjectives = objectiveDAO.getAllObjectives();
        int equalObjects = 0;
        if (objective1.equals(allObjectives.get(0))){
            equalObjects+=1;
        }
        if (objective2.equals(allObjectives.get(1))){
            equalObjects+=1;
        }
        Assert.assertEquals(2, equalObjects, 0);
    }
}
