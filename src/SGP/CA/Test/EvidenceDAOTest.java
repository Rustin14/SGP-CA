package SGP.CA.Test;

import SGP.CA.DataAccess.EvidenceDAO;
import SGP.CA.Domain.Evidence;
import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class EvidenceDAOTest {

    EvidenceDAO evidenceDAO = new EvidenceDAO();

    @Test
    public void saveEvidenceTest() throws SQLException {
        Evidence evidence = new Evidence();
        evidence.setEvidenceTitle("Prototipo de SGP-CA");
        evidence.setDescription("Prototipo completo del sistema SGP-CA.");
        evidence.setEvidenceType("Prototipo");
        LocalDate registrationDate = LocalDate.of(2021, 6, 23);
        java.util.Date utilRegistrationDate = new Date();
        utilRegistrationDate.from(registrationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        evidence.setRegistrationDate(utilRegistrationDate);

        int successfulSave = evidenceDAO.saveEvidence(evidence);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void modifyEvidenceTest() throws SQLException {
        Evidence evidence = new Evidence();
        evidence.setIdEvidence(7);
        evidence.setEvidenceTitle("Prototipo de SGP-CA");
        evidence.setDescription("Prototipo completo del sistema SGP-CA.");
        evidence.setEvidenceType("Prototipo");
        LocalDate registrationDate = LocalDate.of(2021, 6, 23);
        java.util.Date utilRegistrationDate = new Date();
        utilRegistrationDate.from(registrationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        evidence.setRegistrationDate(utilRegistrationDate);
        LocalDate modificationDate = LocalDate.of(2021, 6, 23);
        java.util.Date utilModificationDate = new Date();
        utilModificationDate.from(modificationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        evidence.setModificationDate(utilModificationDate);

        int successfulSave = evidenceDAO.modifyEvidence(evidence, 7);
        Assert.assertEquals(1, successfulSave, 0);
    }

    @Test
    public void deleteEvidenceTest() throws SQLException{
        int successfulTest = evidenceDAO.deleteEvidence(7);

        Assert.assertEquals(1, successfulTest, 0);
    }

    @Test
    public void searchEvidenceByIDEvidenceTest() throws SQLException {
        Evidence evidence = evidenceDAO.searchEvidenceByIDEvidence(7);
        Assert.assertEquals(7, evidence.getIdEvidence(), 0);
    }

    @Test
    public void getAllEvidencesTest() throws SQLException {
        ArrayList<Evidence> allEvidences = evidenceDAO.getAllEvidence();

        Assert.assertEquals(1, allEvidences.get(0).getIdEvidence(), 0);
    }

    @Test
    public void getAllEvidencesFromMemberTest() throws SQLException {
        ArrayList<Evidence> allEvidencesFromMember = evidenceDAO.getAllEvidenceFromMember(6);

        Assert.assertEquals(6, allEvidencesFromMember.get(0).getIdMember());
    }
}
