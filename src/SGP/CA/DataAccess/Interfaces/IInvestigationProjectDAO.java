package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.InvestigationProject;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IInvestigationProjectDAO {
    int saveInvestigationProject(InvestigationProject investigationProject) throws SQLException, ClassNotFoundException;
    InvestigationProject searchInvestigationProjectByTitle(String investigationProjectTitle) throws SQLException, ClassNotFoundException;
    int modifyInvestigationProject (InvestigationProject newInvestigationProject, String oldInvestigationProjectTitle) throws SQLException, ClassNotFoundException;
    int deleteInvestigationProject(String investigationProjectTitle) throws SQLException, ClassNotFoundException;
    ArrayList<InvestigationProject> getAllInvestigationProjects () throws SQLException, ClassNotFoundException;
}
