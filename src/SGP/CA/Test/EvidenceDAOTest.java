package SGP.CA.Test;

import SGP.CA.DataAccess.EvidenceDAO;
import SGP.CA.Domain.Evidence;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;

public class EvidenceDAOTest {

    EvidenceDAO evidenceDAO = new EvidenceDAO();

    @Test
    public void saveEvidenceTest() throws SQLException, ClassNotFoundException {
        Evidence evidence = new Evidence();
        evidence.setEvidenceName("Test");
        evidence.setTypeOfEvidence("Test");
        evidence.setFilePath("Test");
        evidence.setDescription("Test");

        int successfulSave = evidenceDAO.saveEvidence(evidence);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void searchEvidenceByIDEvidenceTest() throws SQLException, ClassNotFoundException {
        Evidence evidence = evidenceDAO.searchEvidenceByIDEvidence(2);
        Assert.assertEquals(2, evidence.getIdEvidence(), 0);
    }

    @Test
    public void getAllEvidencesTest() throws SQLException, ClassNotFoundException {
        ArrayList<Evidence> allEvidences = evidenceDAO.getAllEvidence();

        Assert.assertEquals("Test", allEvidences.get(0).getEvidenceName());
    }
}
