package SGP.CA.GUI;

import SGP.CA.DataAccess.EvidenceDAO;
import SGP.CA.Domain.Evidence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModalConsultEvidenceController implements Initializable {

    @FXML
    Label typeOfEvidenceLabel;
    @FXML
    Label evidenceTitle;
    @FXML
    Label dateLabel;
    @FXML
    Label descriptionLabel;
    @FXML
    Button deleteButton;

    Evidence evidence = Evidence.selectedEvidence;
    AlertBuilder alertBuilder = new AlertBuilder();
    EvidenceDAO evidenceDAO = new EvidenceDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvidenceData();
    }

    public void setEvidenceData() {
        typeOfEvidenceLabel.setText(evidence.getEvidenceType());
        evidenceTitle.setText(evidence.getEvidenceTitle());
        //dateLabel.setText(evidence.getRegistrationDate().toString());
        descriptionLabel.setText(evidence.getDescription());
    }

    public void deleteEvidence() {
        boolean userResponse = alertBuilder.confirmationAlert("¿Está seguro de eliminar al Miembro?");
        int databaseResponse = -1;
        if (userResponse) {
            try {
                databaseResponse = evidenceDAO.deleteEvidence(evidence.getIdEvidence());
            } catch (SQLException sqlException) {
                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            }
        }
        if (databaseResponse == 1) {
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            stage.close();
            ConsultEvidenceController.getInstance().populateTable();
        }
    }



}
