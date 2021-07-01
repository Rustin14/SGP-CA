package SGP.CA.GUI;

import SGP.CA.BusinessLogic.TextValidations;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Application;
import SGP.CA.Domain.InvestigationProject;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import SGP.CA.DataAccess.InvestigationProjectDAO;

public class RegisterInvestigationProjectController extends Application {

    @FXML
    private TextField projectTitleField;

    @FXML
    private TextField endDateField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField lgacField;

    @FXML
    private TextField projectManagerField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField participantsField;

    @FXML
    private Button saveButton;

    @FXML
    private Button exitButton;

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/RegisterInvestigationProjectFXML.fxml"));
            primaryStage.setTitle("Registrar proyecto");
            primaryStage.setScene(new Scene(root, 900, 600));
        }catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        primaryStage.show();
    }

    public void saveButtonEvent () {
        String emptyTextField = checkEmptyTextFields();
        if (!emptyTextField.equals("noEmptyTextFields")) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String errorMessage = "No has llenado el campo " + emptyTextField;
            alertBuilder.errorAlert(errorMessage);
        }else {
            String textFieldExceededLimit = checkTextFieldLimits();
            if (!textFieldExceededLimit.equals("allLimitsRespected")) {
                AlertBuilder alertBuilder = new AlertBuilder();
                String errorMessage = "Limite de texto excedido en el campo " + textFieldExceededLimit;
                alertBuilder.errorAlert(errorMessage);
            }else {
                String validTextFields = validateTextFields();
                if (!validTextFields.equals("allFieldsAreValid")) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String errorMessage = "Solo debes ingresar letras en el campo " + validTextFields;
                    alertBuilder.errorAlert(errorMessage);
                }else {
                    InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                    InvestigationProject investigationProject = new InvestigationProject();
                    investigationProject.setProjectTitle(projectTitleField.getText());
                    try {
                        String endDate = endDateField.getText();
                        Date estimateEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
                        investigationProject.setEstimatedEndDate(estimateEndDate);
                        String stringStartDate = startDateField.getText();
                        Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringStartDate);
                        investigationProject.setStartDate(startDate);
                    }catch (ParseException exParseException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String errorMessage = "La fecha ingresada no esta en el formato dd/MM/yyyy";
                        alertBuilder.errorAlert(errorMessage);
                        return;
                    }
                    investigationProject.setAssociatedLgac(lgacField.getText());
                    investigationProject.setParticipants(participantsField.getText());
                    investigationProject.setProjectManager(projectManagerField.getText());
                    investigationProject.setDescription(descriptionField.getText());
                    int rowsAffectedInvestigationProjectDAO = 0;
                    try {
                        rowsAffectedInvestigationProjectDAO = investigationProjectDAO.saveInvestigationProject(investigationProject);
                    }catch (SQLException exSqlException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                    try {
                        if (rowsAffectedInvestigationProjectDAO == 1) {
                            showConfirmationAlert();
                            Stage stage = (Stage) saveButton.getScene().getWindow();
                            stage.close();
                        }else {
                            showFailedOperationAlert();
                        }
                    }catch (IOException exIoException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "No se cargo correctamente el componente del sistema";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                }
            }
        }
    }

    public void exitButtonEvent() {
        AlertBuilder alertBuilder = new AlertBuilder();
        boolean confirmationMessage = alertBuilder.confirmationAlert("¿Estas seguro que desea salir?");
        if (confirmationMessage) {
            Stage stagePrincipal = (Stage) exitButton.getScene().getWindow();
            stagePrincipal.close();
        }
    }

    public void showConfirmationAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConfirmationAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showFailedOperationAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/FailedRegisterAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public String checkEmptyTextFields() {
        TextValidations textValidations = new TextValidations();
        TextField [] textFields = {projectTitleField, endDateField,
                startDateField, lgacField, projectManagerField, participantsField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Titulo de proyecto", "Fecha estimada de fin", "Fecha de inicio",
                "LGAC asociadas", "Responsable del proyecto", "Participantes"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String emptyTextField = textValidations.checkNoEmptyTextFields(textFieldTexts, textFieldNames);
        if (emptyTextField.equals("noEmptyTextFields")){
            String emptyDescription = textValidations.checkNoEmptyDescription(descriptionField.getText());
            if (!emptyDescription.equals("noEmptyField")){
                return emptyDescription;
            }
        }else{
            return emptyTextField;
        }
        return "noEmptyTextFields";
    }

    public String checkTextFieldLimits() {
        TextValidations textValidations = new TextValidations();
        TextField [] textFields = {projectTitleField, lgacField, projectManagerField, participantsField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Titulo de proyecto", "LGAC asociadas",
                "Responsable del proyecto", "Participantes"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        int [] textLimits = {255, 255, 60, 60};
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String exceedLimitTextField =textValidations.checkTextFieldsLimits(textFieldTexts, textLimits, textFieldNames);
        if (exceedLimitTextField.equals("allLimitsRespected")) {
            String exceededDescriptionLimit = textValidations.checkDescriptionFieldLimit(descriptionField.getText());
            if (!exceededDescriptionLimit.equals("validField")) {
                return exceededDescriptionLimit;
            }
        }else{
            return exceedLimitTextField;
        }
        return "allLimitsRespected";
    }

    public String validateTextFields() {
        TextValidations textValidations = new TextValidations();
        TextField [] textFields = {projectTitleField, lgacField, projectManagerField, participantsField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Titulo de proyecto", "LGAC asociadas",
                "Responsable del proyecto", "Participantes"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String invalidTextField = textValidations.validateTextFields(textFieldTexts, textFieldNames);
        if (invalidTextField.equals("allFieldsAreValid")) {
            String invalidDescriptionField = textValidations.validateDescriptionField(descriptionField.getText());
            if (!invalidDescriptionField.equals("ValidField")) {
                return invalidDescriptionField;
            }
        }else{
            return invalidTextField;
        }
        return "allFieldsAreValid";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
