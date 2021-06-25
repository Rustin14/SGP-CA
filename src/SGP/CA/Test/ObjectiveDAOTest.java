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
    public void saveObjectiveTest() throws SQLException {
        Objective objective = new Objective();
        objective.setObjectiveTitle("Test objective4");
        objective.setDescription("Test description4");
        objective.setStrategy("Test strategy4");

        int successfulSave = objectiveDAO.saveObjective(objective);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void searchObjectiveByTitleTest() throws SQLException {
        Objective objective = objectiveDAO.searchObjectiveByTitle("Probar el SGPCA");

        Assert.assertEquals("Probar el SGPCA",objective.getObjectiveTitle());
    }

    @Test
    public void modifyObjectiveTest() throws SQLException {
        Objective objective = new Objective();
        objective.setObjectiveTitle("Test objective5");
        objective.setDescription("Test description5");
        objective.setStrategy("Test strategy5");

        int successfulSave = objectiveDAO.modifyObjective(objective, "Test objective4");
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void deleteObjectiveTest() throws SQLException {
        String objectiveTitle = "Test objective5";

        int successfulDelete = objectiveDAO.deleteObjective(objectiveTitle);
        Assert.assertEquals(1,successfulDelete, 0);
    }

    @Test
    public void getAllObjectivesTest () throws SQLException {
        Objective objective1 = new Objective();
        objective1.setObjectiveTitle("Probar el SGPCA");
        objective1.setDescription("Poner a prueba el sistema gestor de cuerpos academicos de la FEI" +
                " para ver si puede\noperar bien con los usuarios y es amigable");
        objective1.setStrategy("Estrategia 5");

        Objective objective2 = new Objective();
        objective2.setObjectiveTitle("Probar el SGPCA");
        objective2.setDescription("Poner a prueba el sistema gestor de cuerpos academicos de la FEI" +
                " para ver si puede\noperar bien con los usuarios y es amigable");
        objective2.setStrategy("Estrategia 3");

        Objective objective3 = new Objective();
        objective3.setObjectiveTitle("Probar el SGPCA");
        objective3.setDescription("Poner a prueba el sistema gestor de cuerpos academicos de la FEI" +
                " para ver si puede\noperar bien con los usuarios y es amigable");
        objective3.setStrategy("Estrategia 4");

        Objective objective4 = new Objective();
        objective4.setObjectiveTitle("Objetivo de prueba");
        objective4.setDescription("Descripcion de prueba");
        objective4.setStrategy("Estrategia 2");

        Objective objective5 = new Objective();
        objective5.setObjectiveTitle("Objetivo de prueba");
        objective5.setDescription("Descripcion de prueba");
        objective5.setStrategy("Estrategia 1");

        Objective objective6 = new Objective();
        objective6.setObjectiveTitle("Test objective3");
        objective6.setDescription("Test description3");
        objective6.setStrategy("Test strategy3");

        Objective objective7 = new Objective();
        objective7.setObjectiveTitle("Test objective");
        objective7.setDescription("Test description");
        objective7.setStrategy("Test strategy");

        ArrayList<Objective> allObjectives = objectiveDAO.getAllObjectives();
        int equalObjects = 0;
        if (objective1.equals(allObjectives.get(6))) {
            equalObjects+=1;
        }
        if (objective2.equals(allObjectives.get(5))) {
            equalObjects+=1;
        }
        if (objective3.equals(allObjectives.get(4))) {
            equalObjects+=1;
        }
        if (objective4.equals(allObjectives.get(3))) {
            equalObjects+=1;
        }
        if (objective5.equals(allObjectives.get(2))) {
            equalObjects+=1;
        }
        if (objective6.equals(allObjectives.get(1))) {
            equalObjects+=1;
        }
        if (objective7.equals(allObjectives.get(0))) {
            equalObjects+=1;
        }

        Assert.assertEquals(7, equalObjects, 0);
    }
}
