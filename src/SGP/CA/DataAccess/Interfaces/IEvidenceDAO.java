package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Evidence;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IEvidenceDAO {
    public int saveEvidence(Evidence evidence) throws SQLException;
    public Evidence searchEvidenceByIDEvidence(int idEvidence) throws SQLException;
    public ArrayList<Evidence> getAllEvidence() throws SQLException;
}
