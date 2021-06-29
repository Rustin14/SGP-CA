package SGP.CA.GUI;

import SGP.CA.BusinessLogic.TextValidations;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import SGP.CA.DataAccess.BluePrintDAO;
import SGP.CA.Domain.BluePrint;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ModifyBluePrintController extends Application {

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField receptionWorkNameTextField;

    @FXML
    private TextField bluePrintTitleTextField;

    @FXML
    private TextField starDateTextField;

    @FXML
    private TextField associatedLgacTextField;

    @FXML
    private TextField stateTextField;

    @FXML
    private TextField directorTextField;

    @FXML
    private TextField coDirectorTextField;

    @FXML
    private TextField durationTextField;

    @FXML
    private TextField modalityTextField;

    @FXML
    private TextField requirementsTextField;

    @FXML
    private TextField studentTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox<String> bluePrintsComboBox;

    private BluePrintDAO bluePrintDAO = new BluePrintDAO();

    private ArrayList<BluePrint> bluePrints = new ArrayList<>();

    private ObservableList<String> titles = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyBluePrintFXML.fxml"));
            primaryStage.setTitle("Modificar anteproyecto ");
            primaryStage.setScene(new Scene(root, 900, 600));
        }catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        primaryStage.show();
    }

    public void bluePrintSelectedEvent() {
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String titleSelected = bluePrintsComboBox.getSelectionModel().getSelectedItem();
        BluePrint bluePrint = new BluePrint();
        try {
            bluePrint = bluePrintDAO.searchBluePrintByTitle(titleSelected);
        }catch (SQLException sqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        bluePrintTitleTextField.setText(bluePrint.getBluePrintTitle());
        associatedLgacTextField.setText(bluePrint.getAssociatedLgac());
        String startDate = setDate.format(bluePrint.getStartDate());
        starDateTextField.setText(startDate);
        stateTextField.setText(bluePrint.getState());
        directorTextField.setText(bluePrint.getDirector());
        coDirectorTextField.setText(bluePrint.getCoDirector());
        durationTextField.setText(String.valueOf(bluePrint.getDuration()));
        modalityTextField.setText(bluePrint.getModality());
        receptionWorkNameTextField.setText(bluePrint.getReceptionWorkName());
        requirementsTextField.setText(bluePrint.getRequirements());
        studentTextField.setText(bluePrint.getStudent());
        descriptionTextArea.setText(bluePrint.getDescription());
    }

    public void cancelButtonEvent() {
        try {
            showExitAlert();
        }catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }

    }

    public void saveButtonEvent() {
        String noEmptyTextField = checkEmptyTextFields();
        if (!noEmptyTextField.equals("noEmptyTextFields")) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String errorMessage = "No has llenado el campo "+ noEmptyTextField;
            alertBuilder.errorAlert(errorMessage);
        }else {
            String noExceededLimitText = checkTextFieldLimits();
            if (!noExceededLimitText.equals("allLimitsRespected")) {
                AlertBuilder alertBuilder = new AlertBuilder();
                String errorMessage = "Limite de texto excedido en el campo " + noExceededLimitText;
                alertBuilder.errorAlert(errorMessage);
            }else {
                String validStringFields = validateTextFields();
                if (!validStringFields.equals("allFieldsAreValid")) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String errorMessage;
                    if (validStringFields.equals("Duracion")){
                        errorMessage = "Solo debes ingresar numeros en el campo de texto "+ validStringFields;
                    }else{
                        errorMessage = "Solo debes ingresar letras en el campo de texto "+ validStringFields;
                    }
                    alertBuilder.errorAlert(errorMessage);
                }else {
                    DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
                    String titleSelected = bluePrintsComboBox.getSelectionModel().getSelectedItem();
                    BluePrint bluePrint = new BluePrint();
                    bluePrint.setBluePrintTitle(bluePrintTitleTextField.getText());
                    String startDateString = starDateTextField.getText();
                    Date startDate = new Date();
                    try {
                        startDate = setDate.parse(startDateString);
                    }catch (ParseException exParseException){
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String errorMessage = "La fecha ingresada no esta en el formato dd/MM/yyyy";
                        alertBuilder.errorAlert(errorMessage);
                        return;
                    }
                    bluePrint.setStartDate(startDate);
                    bluePrint.setAssociatedLgac(associatedLgacTextField.getText());
                    bluePrint.setState(stateTextField.getText());
                    bluePrint.setDirector(directorTextField.getText());
                    bluePrint.setCoDirector(coDirectorTextField.getText());
                    bluePrint.setDuration(Integer.parseInt(durationTextField.getText()));
                    bluePrint.setModality(modalityTextField.getText());
                    bluePrint.setReceptionWorkName(receptionWorkNameTextField.getText());
                    bluePrint.setRequirements(requirementsTextField.getText());
                    bluePrint.setStudent(studentTextField.getText());
                    bluePrint.setDescription(descriptionTextArea.getText());
                    int rowsAffectedBluePrintDAO = 0;
                    try {
                        rowsAffectedBluePrintDAO = bluePrintDAO.modifyBluePrint(bluePrint, titleSelected);
                    }catch (SQLException exSqlException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                    try {
                        if (rowsAffectedBluePrintDAO == 1) {
                            showSuccessfulModifyConfirmationAlert();
                            Stage stage = (Stage) saveButton.getScene().getWindow();
                            stage.close();
                        }else {
                            showFailedOperationAlert();
                        }
                    }catch (IOException exIoException){
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "No se cargo correctamente el componente del sistema";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                }
            }
        }
    }

    @FXML
    public void initialize() {
        try {
            bluePrints = bluePrintDAO.getAllBluePrints();
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        for (BluePrint blueprint : bluePrints) {
            titles.add(blueprint.getBluePrintTitle());
        }
        bluePrintsComboBox.setItems(titles);
    }

    public void showFailedOperationAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/FailedRegisterAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showSuccessfulModifyConfirmationAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConfirmationModifyBluePrintAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showExitAlert() throws IOException {
        AlertBuilder alertBuilder = new AlertBuilder();
        boolean confirmationMessage = alertBuilder.confirmationAlert("¿Estas seguro que desea salir?");
        if (confirmationMessage) {
            Stage stagePrincipal = (Stage) cancelButton.getScene().getWindow();
            stagePrincipal.close();
        }
    }

    public String checkEmptyTextFields() {
        TextValidations textValidations = new TextValidations();
        TextField [] textFields = {bluePrintTitleTextField, starDateTextField, associatedLgacTextField, stateTextField,
                directorTextField, coDirectorTextField, modalityTextField, receptionWorkNameTextField,
                requirementsTextField, studentTextField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Titulo de anteproyecto", "Fecha de inicio",
                "LGAC asociadas", "Estado", "Director", "Codirectores", "Modalidad", "Titulo de trabajo recepcional",
                "Requisitos", "Estudiantes"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String emptyTextField = textValidations.checkNoEmptyTextFields(textFieldTexts, textFieldNames);
        if (emptyTextField.equals("noEmptyTextFields")){
            String emptyDescription = textValidations.checkNoEmptyDescription(descriptionTextArea.getText());
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
        TextField [] textFields = {bluePrintTitleTextField, associatedLgacTextField, stateTextField, directorTextField,
                coDirectorTextField, modalityTextField, receptionWorkNameTextField, requirementsTextField,
                studentTextField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Titulo de anteproyecto", "LGAC asociadas",
                "Estado", "Director", "Codirector", "Modalidad", "Titulo de trabajo recepcional", "Requisitos",
                "Estudiante"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        int [] textLimits = {255, 255, 50, 60, 50, 30, 120, 120, 50};
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String exceedLimitTextField =textValidations.checkTextFieldsLimits(textFieldTexts, textLimits, textFieldNames);
        if (exceedLimitTextField.equals("allLimitsRespected")) {
            String exceededDescriptionLimit = textValidations.checkDescriptionFieldLimit(descriptionTextArea.getText());
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
        ArrayList<String> numberFieldTexts = new ArrayList<>(Arrays.asList(durationTextField.getText()));
        ArrayList<String> numberFieldNames = new ArrayList<>(Arrays.asList("Duracion"));
        TextField [] textFields = {bluePrintTitleTextField, associatedLgacTextField, stateTextField, directorTextField,
                coDirectorTextField, modalityTextField, receptionWorkNameTextField, requirementsTextField,
                studentTextField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Titulo de anteproyecto", "LGAC asociadas",
                "Estado", "Director", "Codirector", "Modalidad", "Titulo de trabajo recepcional", "Requisitos",
                "Estudiante"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String invalidTextField = textValidations.validateTextFields(textFieldTexts, textFieldNames);
        if (invalidTextField.equals("allFieldsAreValid")) {
            String invalidNumberField = textValidations.validateNumberFields(numberFieldTexts, numberFieldNames);
            if (invalidNumberField.equals("allFieldsAreValid")){
                String invalidDescriptionField = textValidations.validateDescriptionField(descriptionTextArea.getText());
                if (!invalidDescriptionField.equals("ValidField")) {
                    return invalidDescriptionField;
                }
            }else{
                return invalidNumberField;
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
