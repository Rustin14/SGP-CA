package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Objective;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IObjectiveDAO {
    int saveObjective(Objective objective) throws SQLException, ClassNotFoundException;
    Objective searchObjectiveByTitle(String objectiveTitle) throws SQLException, ClassNotFoundException;
    int modifyObjective (Objective newObjective, String oldObjectiveTitle) throws SQLException, ClassNotFoundException;
    int deleteObjective (String objectiveTitle) throws SQLException, ClassNotFoundException;
    ArrayList<Objective> getAllObjectives () throws SQLException, ClassNotFoundException;
}
