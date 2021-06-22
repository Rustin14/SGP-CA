package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Objective;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IObjectiveDAO {
    int saveObjective(Objective objective) throws SQLException;
    Objective searchObjectiveByTitle(String objectiveTitle) throws SQLException;
    int modifyObjective (Objective newObjective, String oldObjectiveTitle) throws SQLException;
    int deleteObjective (String objectiveTitle) throws SQLException;
    ArrayList<Objective> getAllObjectives () throws SQLException;
}
