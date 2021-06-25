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
    public void saveObjectiveTest() throws SQLException{
        Objective objective = new Objective();
        objective.setObjectiveTitle("Test objective4");
        objective.setDescription("Test description4");
        objective.setStrategy("Test strategy4");

        int successfulSave = objectiveDAO.saveObjective(objective);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void searchObjectiveByTitleTest() throws SQLException{
        Objective objective = objectiveDAO.searchObjectiveByTitle("Test objective4");

        Assert.assertEquals("Test objective4",objective.getObjectiveTitle());
    }

    @Test
    public void modifyObjective () throws SQLException{
        Objective objective = new Objective();
        objective.setObjectiveTitle("Test objective5");
        objective.setDescription("Test description5");
        objective.setStrategy("Test strategy5");

        int successfulSave = objectiveDAO.modifyObjective(objective, "Test objective4");
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void deleteObjectiveTest() throws SQLException{
        String objectiveTitle = "Test objective5";

        int successfulDelete = objectiveDAO.deleteObjective(objectiveTitle);
        Assert.assertEquals(1,successfulDelete, 0);
    }

    @Test
    public void getAllObjectivesTest () throws SQLException{
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
