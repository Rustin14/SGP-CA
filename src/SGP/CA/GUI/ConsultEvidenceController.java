package SGP.CA.GUI;

import SGP.CA.BusinessLogic.ProjectUtilities;
import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.EvidenceDAO;
import SGP.CA.Domain.Evidence;
import SGP.CA.Domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultEvidenceController implements Initializable {

    @FXML
    TableView evidenceTable;
    @FXML
    TableColumn evidenceTitleColumn;
    @FXML
    TableColumn evidenceTypeColumn;
    @FXML
    TextField searchBar;
    @FXML
    Label profileLabel;
    @FXML
    Label evidencesLabel;
    @FXML
    Label eventsLabel;
    @FXML
    Label projectsLabel;
    @FXML
    Label workPlanLabel;
    @FXML
    Label membersLabel;

    private static ConsultEvidenceController consultEvidenceControllerInstance;

    public ConsultEvidenceController () {
        consultEvidenceControllerInstance = this;
    }

    public static ConsultEvidenceController getInstance() {
        return consultEvidenceControllerInstance;
    }

    Member member = Member.signedMember;

    EvidenceDAO evidenceDAO = new EvidenceDAO();
    ArrayList<Evidence> allEvidence = new ArrayList<Evidence>();
    ObservableList<Evidence> evidenceList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
        setLabelActions();
        ProjectUtilities.openDataModal(evidenceTable, "FXML/ModalConsultEvidenceFXML.fxml");
        ProjectUtilities.searchBar(searchBar, evidenceTable, evidenceList);
        ProjectUtilities.setTextLimit(searchBar, 50);
    }

    public void populateTable() {
        ArrayList<Evidence> allActiveEvidences = new ArrayList<>();
        ObservableList<Evidence> auxiliarEvidenceList = FXCollections.observableArrayList();

        try {
            allEvidence = evidenceDAO.getAllEvidenceFromMember(member.getIdMember());
        } catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        } finally {
            try {
                ConnectDB.closeConnection();
            } catch (SQLException exSqlException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            }
        }
        for (int i = 0; i < allEvidence.size(); i++) {
            if (allEvidence.get(i).getActive() == 1) {
                    allActiveEvidences.add(allEvidence.get(i));
                    auxiliarEvidenceList.add(allEvidence.get(i));
            }
        }
        evidenceList = auxiliarEvidenceList;
        ProjectUtilities.searchBar(searchBar, evidenceTable, evidenceList);
    }

    public void registerEvidence() {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        try {
            sceneSwitcher.createDialog((Stage) evidenceTable.getScene().getWindow(), "FXML/RegisterEvidenceFXML.fxml");
        } catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        }
    }

    public void setListView() {
        evidenceTitleColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("evidenceTitle"));
        evidenceTypeColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("evidenceType"));
        populateTable();
    }

    public void setLabelActions() {
        if (Member.signedMember.getIsResponsible() == 1) {
            profileLabel.setOnMouseClicked(event -> SceneSwitcher.goToResponsibleProfile());
            membersLabel.setOnMouseClicked(event -> SceneSwitcher.consultMembers());
            evidencesLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleEvidences());
            workPlanLabel.setOnMouseClicked(event -> SceneSwitcher.consultWorkPlan());
            projectsLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleProjects());
            eventsLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleEvents());
        } else {
            profileLabel.setOnMouseClicked(event -> SceneSwitcher.goToMemberProfile());
            evidencesLabel.setOnMouseClicked(event -> SceneSwitcher.consultEvidence());
            projectsLabel.setOnMouseClicked(event -> SceneSwitcher.consultProjects());
        }
    }



}
