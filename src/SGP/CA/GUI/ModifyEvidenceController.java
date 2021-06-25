package SGP.CA.GUI;

import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.EvidenceDAO;
import SGP.CA.Domain.Evidence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyEvidenceController implements Initializable {

    @FXML
    ComboBox evidenceTypeCombo;
    @FXML
    TextField evidenceTitleTextField;
    @FXML
    TextField registrationDatePicker;
    @FXML
    TextField modifyingDatePicker;
    @FXML
    TextArea descriptionArea;
    @FXML
    Button modifyButton;
    @FXML
    Label areaCharacters;

    Evidence evidenceToModify = Evidence.selectedEvidence;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvidenceData();
        setTextLimit();
    }

    public boolean validateFields() {
        List<TextField> textFields = Arrays.asList(evidenceTitleTextField);
        for (TextField field : textFields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }

        if (evidenceTypeCombo.getSelectionModel().isEmpty()) {
            return false;
        }

        if (descriptionArea.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public void setTextLimit() {
        final int MAX_CHARS = 252 ;

        descriptionArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));

        evidenceTitleTextField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));

        areaCharacters.textProperty().bind(descriptionArea.textProperty().length().asString("%d/252"));
    }

    private void setEvidenceData() {
        evidenceTypeCombo.setValue(evidenceToModify.getEvidenceType());
        evidenceTitleTextField.setText(evidenceToModify.getEvidenceTitle());
        String patternDate = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(patternDate);
        String registrationDate = dateFormat.format(evidenceToModify.getRegistrationDate());
        registrationDatePicker.setText(registrationDate);
        String modifyingDate = dateFormat.format(Calendar.getInstance().getTime());
        modifyingDatePicker.setText(modifyingDate);
        descriptionArea.setText(evidenceToModify.getDescription());
    }

    private Evidence createEvidence() {
        Evidence evidence = new Evidence();
        if (validateFields()) {
            evidence.setIdEvidence(evidenceToModify.getIdEvidence());
            evidence.setEvidenceType(evidenceTypeCombo.getValue().toString());
            evidence.setEvidenceTitle(evidenceTitleTextField.getText());
            evidence.setRegistrationDate(evidenceToModify.getRegistrationDate());
            evidence.setModificationDate(new Date());
            evidence.setDescription(descriptionArea.getText());
        }
        return evidence;
    }

    public void modifyEvidence() {
        EvidenceDAO evidenceDAO = new EvidenceDAO();
        AlertBuilder alertBuilder = new AlertBuilder();
        Evidence evidence = createEvidence();
        int successfulUpdate = -2;
        try {
            successfulUpdate = evidenceDAO.modifyEvidence(evidence, evidence.getIdEvidence());
        } catch (SQLException exSqlException) {
            alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
        } finally {
            try {
                ConnectDB.closeConnection();
            } catch (SQLException exSqlException) {
                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            }
        }
        if (successfulUpdate == 1) {
            alertBuilder.successAlert("¡Registro exitoso!");
            Stage currentStage = (Stage) modifyButton.getScene().getWindow();
            currentStage.close();
            Stage closeEvidence = (Stage) ModalConsultEvidenceController.getInstance().deleteButton.getScene().getWindow();
            closeEvidence.close();
            if (ConsultEvidenceController.getInstance() == null) {
                ConsultEvidenceResponsibleController.getInstance().populateTable();
            } else {
                ConsultEvidenceController.getInstance().populateTable();
            }
        }
    }

    public void cancelButton() {
        Stage stage = (Stage) modifyButton.getScene().getWindow();
        stage.close();
    }
}
