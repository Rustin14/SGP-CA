package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Evidence;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IEvidenceDAO {
    public int saveEvidence(Evidence evidence) throws SQLException, ClassNotFoundException;
    public Evidence searchEvidenceByIDEvidence(int idEvidence) throws SQLException, ClassNotFoundException;
    public ArrayList<Evidence> getAllEvidence() throws SQLException, ClassNotFoundException;
}
