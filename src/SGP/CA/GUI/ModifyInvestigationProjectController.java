package SGP.CA.GUI;

import SGP.CA.BusinessLogic.TextValidations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Application;
import SGP.CA.Domain.InvestigationProject;
import SGP.CA.DataAccess.InvestigationProjectDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ModifyInvestigationProjectController extends Application {

    @FXML
    private Button exitButton;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField projectTitleField;

    @FXML
    private TextField endDateField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField lgacField;

    @FXML
    private TextField participantsField;

    @FXML
    private TextField projectManagerField;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> projectsTitleComboBox;

    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyInvestigationProjectFXML.fxml"));
            primaryStage.setTitle("Modificar proyecto");
            primaryStage.setScene(new Scene(root, 900, 600));
        }catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        primaryStage.show();
    }

    public void saveButtonEvent() {
        String noEmptyTextField = checkEmptyTextFields();
        if (!noEmptyTextField.equals("noEmptyTextFields")) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String errorMessage = "No has llenado todos los campos";
            alertBuilder.errorAlert(errorMessage);
        }else {
            String noExceededTextLimit = checkTextFieldLimits();
            if (!noExceededTextLimit.equals("allLimitsRespected")) {
                AlertBuilder alertBuilder = new AlertBuilder();
                String errorMessage = "Limite de texto excedido en algun campo";
                alertBuilder.errorAlert(errorMessage);
            }else {
                String validStringFields = validateTextFields();
                if (!validStringFields.equals("allFieldsAreValid")) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String errorMessage = "Solo debes ingresar letras en los campos que no sean fecha de inicio y fecha de finalizacion";
                    alertBuilder.errorAlert(errorMessage);
                }else {
                    InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                    String oldTitle = projectsTitleComboBox.getSelectionModel().getSelectedItem();
                    InvestigationProject investigationProject = new InvestigationProject();
                    investigationProject.setProjectTitle(projectTitleField.getText());
                    String endDate = endDateField.getText();
                    try {
                        Date estimateEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
                        investigationProject.setEstimatedEndDate(estimateEndDate);
                        String startDateString = startDateField.getText();
                        Date starDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
                        investigationProject.setStartDate(starDate);
                    }catch (ParseException exParseException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String errorMessage = "La fecha ingresada no esta en el formato dd/MM/yyyy";
                        alertBuilder.errorAlert(errorMessage);
                    }
                    investigationProject.setAssociatedLgac(lgacField.getText());
                    investigationProject.setParticipants(participantsField.getText());
                    investigationProject.setProjectManager(projectManagerField.getText());
                    investigationProject.setDescription(descriptionField.getText());
                    int rowsAffectedInvestigationProjectDAO = 0;
                    try {
                        rowsAffectedInvestigationProjectDAO = investigationProjectDAO.modifyInvestigationProject(investigationProject, oldTitle);
                    }catch (SQLException exSqlException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                    if (rowsAffectedInvestigationProjectDAO == 1) {
                        try {
                            showConfirmationAlert();
                            Stage stagePrincipal = (Stage) saveButton.getScene().getWindow();
                            stagePrincipal.close();
                        }catch (IOException exIoException) {
                            AlertBuilder alertBuilder = new AlertBuilder();
                            String exceptionMessage = "No se cargo correctamente el componente del sistema";
                            alertBuilder.exceptionAlert(exceptionMessage);
                        }
                    }else {
                        try {
                            showFailedOperationAlert();
                        }catch (IOException exIoException) {
                            AlertBuilder alertBuilder = new AlertBuilder();
                            String exceptionMessage = "No se cargo correctamente el componente del sistema";
                            alertBuilder.exceptionAlert(exceptionMessage);
                        }
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

    public void projectsTitleComboBoxEvent() {
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        String title = projectsTitleComboBox.getSelectionModel().getSelectedItem();
        InvestigationProject investigationProject = new InvestigationProject();
        try {
            investigationProject = investigationProjectDAO.searchInvestigationProjectByTitle(title);
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        projectTitleField.setText(investigationProject.getProjectTitle());
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String endingDate = setDate.format(investigationProject.getEstimatedEndDate().getTime());
        endDateField.setText(endingDate);
        String startDate = setDate.format(investigationProject.getStartDate().getTime());
        startDateField.setText(startDate);
        lgacField.setText(investigationProject.getAssociatedLgac());
        participantsField.setText(investigationProject.getParticipants());
        projectManagerField.setText(investigationProject.getProjectManager());
        descriptionField.setText(investigationProject.getDescription());
    }

    @FXML
    public void initialize() {
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        ArrayList<InvestigationProject> allProjects = new ArrayList<>();
        try {
            allProjects = investigationProjectDAO.getAllInvestigationProjects();
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        ObservableList<String> allProjectsTile = FXCollections.observableArrayList();
        for (int i=0; i< allProjects.size(); i++) {
            allProjectsTile.add(allProjects.get(i).getProjectTitle());
        }
        projectsTitleComboBox.setItems(allProjectsTile);
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
