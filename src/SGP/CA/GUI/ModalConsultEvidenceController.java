package SGP.CA.GUI;

import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.EvidenceDAO;
import SGP.CA.Domain.Evidence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    private static ModalConsultEvidenceController instance;

    public static ModalConsultEvidenceController getInstance() {
        return instance;
    }

    public ModalConsultEvidenceController () {
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvidenceData();
    }

    public void setEvidenceData() {
        typeOfEvidenceLabel.setText(evidence.getEvidenceType());
        evidenceTitle.setText(evidence.getEvidenceTitle());
        String patternDate = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(patternDate);
        String registrationDate = dateFormat.format(evidence.getRegistrationDate());
        dateLabel.setText(registrationDate);
        descriptionLabel.setText(evidence.getDescription());
    }

    public void deleteEvidence() {
        boolean userResponse = alertBuilder.confirmationAlert("¿Está seguro de eliminar al Miembro?");
        int databaseResponse = -1;
        if (userResponse) {
            try {
                databaseResponse = evidenceDAO.deleteEvidence(evidence.getIdEvidence());
            } catch (SQLException exSqlException) {
                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            } finally {
                try {
                    ConnectDB.closeConnection();
                } catch (SQLException exSqlException) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
                }
            }
        }
        if (databaseResponse == 1) {
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            stage.close();
            if (ConsultEvidenceController.getInstance() == null) {
                ConsultEvidenceResponsibleController.getInstance().populateTable();
            } else {
                ConsultEvidenceController.getInstance().populateTable();
            }
        }
    }

    public void modifyEvidence() {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        try {
            sceneSwitcher.createDialog((Stage) deleteButton.getScene().getWindow(), "FXML/ModifyEvidenceFXML.fxml");
        } catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible acceder a esta ventana. Intente más tarde.");
        }
    }

    public void cancelButton() {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }



}
