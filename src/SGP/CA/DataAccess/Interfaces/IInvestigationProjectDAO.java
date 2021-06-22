package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.InvestigationProject;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IInvestigationProjectDAO {
    int saveInvestigationProject(InvestigationProject investigationProject) throws SQLException;
    InvestigationProject searchInvestigationProjectByTitle(String investigationProjectTitle) throws SQLException;
    int modifyInvestigationProject (InvestigationProject newInvestigationProject, String oldInvestigationProjectTitle) throws SQLException;
    int deleteInvestigationProject(String investigationProjectTitle) throws SQLException;
    ArrayList<InvestigationProject> getAllInvestigationProjects () throws SQLException;
}
