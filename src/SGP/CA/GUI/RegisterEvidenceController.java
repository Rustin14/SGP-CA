package SGP.CA.GUI;

import SGP.CA.DataAccess.EvidenceDAO;
import SGP.CA.Domain.Evidence;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RegisterEvidenceController {

    @FXML
    TextField evidenceTitleTextField;
    @FXML
    ComboBox evidenceTypeCombo;
    @FXML
    DatePicker registrationDatePicker;
    @FXML
    TextArea descriptionArea;

    EvidenceDAO evidenceDAO = new EvidenceDAO();
    AlertBuilder alertBuilder = new AlertBuilder();

    public boolean checkEmptyFields() {
        List<TextField> textFields = Arrays.asList(evidenceTitleTextField);
        for (TextField field : textFields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }

        if (evidenceTypeCombo.getSelectionModel().isEmpty()) {
            return false;
        }

        if (registrationDatePicker.getValue() == null) {
            return false;
        }

        if (descriptionArea.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public Evidence createEvidence() {
        Evidence evidence = new Evidence();

        if (checkEmptyFields()) {
            evidence.setEvidenceTitle(evidenceTitleTextField.getText());
            Date registrationDate = Date.from(registrationDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            evidence.setRegistrationDate(registrationDate);
            evidence.setEvidenceType(evidenceTypeCombo.getValue().toString());
            evidence.setDescription(descriptionArea.getText());
        }

        return evidence;
    }

    public void saveEvidence() {
        Evidence evidence = createEvidence();
        int successfulSave = 0;
        if (evidence != null) {
            try {
                successfulSave = evidenceDAO.saveEvidence(evidence);
            } catch (SQLException sqlException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
                sqlException.printStackTrace();
            }
        }
        alertBuilder.successAlert("¡Registro realizado!");
        Stage stage = (Stage) evidenceTypeCombo.getScene().getWindow();
        stage.close();
        ConsultEvidenceController.getInstance().populateTable();
    }

}
