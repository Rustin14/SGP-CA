package SGP.CA.GUI;

import SGP.CA.DataAccess.EvidenceDAO;
import SGP.CA.Domain.Evidence;
import SGP.CA.Domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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

    private static ConsultEvidenceController consultEvidenceControllerInstance;

    public ConsultEvidenceController () {
        consultEvidenceControllerInstance = this;
    }

    public static ConsultEvidenceController getInstance() {
        return consultEvidenceControllerInstance;
    }

    EvidenceDAO evidenceDAO = new EvidenceDAO();
    ArrayList<Evidence> allEvidence = new ArrayList<Evidence>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
        openMemberDataModal();
    }

    public void populateTable() {
        ArrayList<Evidence> allActiveEvidences = new ArrayList<>();
        try {
            allEvidence = evidenceDAO.getAllEvidence();
        } catch (SQLException sqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            sqlException.printStackTrace();
        }
        for (int i = 0; i < allEvidence.size(); i++) {
            if (allEvidence.get(i).getActive() == 1) {
                allActiveEvidences.add(allEvidence.get(i));
            }
        }
        evidenceTable.getItems().setAll(allActiveEvidences);
    }

    public void registerEvidence() {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        try {
            sceneSwitcher.createDialog((Stage) evidenceTable.getScene().getWindow(), "FXML/RegisterEvidenceFXML.fxml");
        } catch (IOException ioException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        }
    }

    public void setListView() {
        evidenceTitleColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("evidenceTitle"));
        evidenceTypeColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("evidenceType"));
        populateTable();
    }

    public void openMemberDataModal() {
        evidenceTable.setRowFactory( tv -> {
            TableRow<Evidence> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Evidence.selectedEvidence = row.getItem();
                    SceneSwitcher sceneSwitcher = new SceneSwitcher();
                    try {
                        sceneSwitcher.createDialog((Stage) evidenceTable.getScene().getWindow(), "FXML/ModalConsultEvidenceFXML.fxml");
                    } catch (IOException ioException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        alertBuilder.exceptionAlert("Error cargando la ventana. Intente de nuevo.");
                        ioException.printStackTrace();
                    }
                }
            });
            return row ;
        });
    }



}
