package SGP.CA.GUI;

import SGP.CA.DataAccess.EvidenceDAO;
import SGP.CA.Domain.Evidence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RegisterEvidenceController implements Initializable {

    @FXML
    TextField evidenceTitleTextField;
    @FXML
    ComboBox evidenceTypeCombo;
    @FXML
    TextField registrationDatePicker;
    @FXML
    TextArea descriptionArea;
    @FXML
    Label areaCharacters;

    EvidenceDAO evidenceDAO = new EvidenceDAO();
    AlertBuilder alertBuilder = new AlertBuilder();
    Evidence evidence;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRegistrationDate();
        setTextLimit();
    }

    public boolean checkEmptyFields() {
        if (evidenceTitleTextField.getText().isEmpty()) {
            return false;
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

    public boolean createEvidence() {
        boolean successfulCreation = false;
        if (checkEmptyFields()) {
            evidence = new Evidence();
            evidence.setEvidenceTitle(evidenceTitleTextField.getText());
            evidence.setEvidenceType(evidenceTypeCombo.getValue().toString());
            evidence.setRegistrationDate(new Date());
            evidence.setDescription(descriptionArea.getText());
            successfulCreation = true;
        }
        return successfulCreation;
    }

    public void saveEvidence() {
        createEvidence();
        int successfulSave = 0;
        if (createEvidence()) {
            try {
                successfulSave = evidenceDAO.saveEvidence(evidence);
            } catch (SQLException sqlException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
                sqlException.printStackTrace();
            }
        } else {
            alertBuilder.errorAlert("No dejar campos vacíos.");
        }
        if (successfulSave == 1) {
            alertBuilder.successAlert("¡Registro realizado!");
            Stage stage = (Stage) evidenceTypeCombo.getScene().getWindow();
            stage.close();
            ConsultEvidenceController.getInstance().populateTable();
        }
    }

    public void setRegistrationDate() {
        String patternDate = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(patternDate);
        String registrationDate = dateFormat.format(Calendar.getInstance().getTime());
        registrationDatePicker.setText(registrationDate);
    }


}
