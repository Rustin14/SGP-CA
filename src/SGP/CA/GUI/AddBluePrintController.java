package SGP.CA.GUI;

import SGP.CA.BusinessLogic.TextValidations;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import SGP.CA.Domain.BluePrint;
import SGP.CA.DataAccess.BluePrintDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddBluePrintController extends Application {

    @FXML
    private Button saveButton;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField bluePrintTitleField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField lgacField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField coDirectorFIeld;

    @FXML
    private TextField durationField;

    @FXML
    private TextField modalityField;

    @FXML
    private TextField studentField;

    @FXML
    private TextField requirementsTextField;

    @FXML
    private TextField receptionWorkName;

    @FXML
    private TextField directorTextField;

        @Override
        public void start(Stage primaryStage) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXML/AddBluePrintFXML.fxml"));
                primaryStage.setTitle("Registrar anteproyecto");
                primaryStage.setScene(new Scene(root, 900, 600));
                primaryStage.show();
            }catch (IOException ioException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
        }

        public void exitButtonEvent() {
            AlertBuilder alertBuilder = new AlertBuilder();
            boolean confirmationMessage = alertBuilder.confirmationAlert("¿Estas seguro que desea salir del proyecto?");
            if (confirmationMessage){
                Stage stagePrincipal = (Stage) saveButton.getScene().getWindow();
                stagePrincipal.close();
            }
        }

        public void saveButtonEvent() {
            String noEmptyTextFields = checkEmptyTextFields();
            if(!noEmptyTextFields.equals("noEmptyTextFields")) {
                AlertBuilder alertBuilder = new AlertBuilder();
                String errorMessage = "No has llenado el campo " + noEmptyTextFields;
                alertBuilder.errorAlert(errorMessage);
            }else {
                String noExceededLimitSize = checkTextFieldLimits();
                if(!noExceededLimitSize.equals("allLimitsRespected")) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String errorMessage = "Limite de texto excedido en el campo " + noExceededLimitSize;
                    alertBuilder.errorAlert(errorMessage);
                }else {
                    String validStringTextFields = validateTextFields();
                    if(!validStringTextFields.equals("allFieldsAreValid")) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String errorMessage;
                        if (validStringTextFields.equals("Duracion")){
                            errorMessage = "Solo debes ingresar numeros en el campo de texto "+ validStringTextFields;
                        }else{
                            errorMessage = "Solo debes ingresar letras en el campo de texto "+ validStringTextFields;
                        }
                        alertBuilder.errorAlert(errorMessage);
                    }else {
                        BluePrint bluePrint = new BluePrint();
                        BluePrintDAO bluePrintDAO = new BluePrintDAO();
                        bluePrint.setBluePrintTitle(bluePrintTitleField.getText());
                        bluePrint.setDescription(descriptionField.getText());
                        bluePrint.setCoDirector(coDirectorFIeld.getText());
                        bluePrint.setDuration(Integer.parseInt(durationField.getText()));
                        bluePrint.setModality(modalityField.getText());
                        bluePrint.setStudent(studentField.getText());
                        bluePrint.setAssociatedLgac(lgacField.getText());
                        bluePrint.setState(stateField.getText());
                        bluePrint.setRequirements(requirementsTextField.getText());
                        bluePrint.setReceptionWorkName(receptionWorkName.getText());
                        bluePrint.setDirector(directorTextField.getText());
                        try {
                            String stringStartDate = startDateField.getText();
                            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringStartDate);
                            bluePrint.setStartDate(startDate);
                            int action = bluePrintDAO.saveBluePrint(bluePrint);
                            if (action == 1) {
                                showConfirmationAlert();
                                Stage stagePrincipal = (Stage) saveButton.getScene().getWindow();
                                stagePrincipal.close();
                            }else {
                                showFailedRegisterAlert();
                            }
                        }catch (ParseException exParseException) {
                            AlertBuilder alertBuilder = new AlertBuilder();
                            String errorMessage = "La fecha ingresada no esta en el formato dd/MM/yyyy";
                            alertBuilder.errorAlert(errorMessage);
                            return;
                        }catch (SQLException sqlException) {
                            AlertBuilder alertBuilder = new AlertBuilder();
                            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
                            alertBuilder.exceptionAlert(exceptionMessage);
                        }catch (IOException ioException) {
                            AlertBuilder alertBuilder = new AlertBuilder();
                            String exceptionMessage = "No se cargo correctamente el componente del sistema";
                            alertBuilder.exceptionAlert(exceptionMessage);
                        }
                    }
                }
            }
        }

    public void showConfirmationAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/SaveBluePrintConfirmationAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showFailedRegisterAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/FailedRegisterAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public String checkEmptyTextFields() {
        TextValidations textValidations = new TextValidations();
        TextField [] textFields = {bluePrintTitleField, startDateField,lgacField, stateField, directorTextField,
                coDirectorFIeld, modalityField, receptionWorkName, requirementsTextField, studentField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Titulo de anteproyecto", "Fecha de inicio",
                "LGAC asociadas", "Estado", "Director", "Codirectores", "Modalidad", "Titulo de trabajo recepcional",
                "Requisitos", "Estudiantes"));
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
        TextField [] textFields = {bluePrintTitleField, lgacField, stateField, directorTextField, coDirectorFIeld,
                modalityField, receptionWorkName, requirementsTextField, studentField};
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
        ArrayList<String> numberFieldTexts = new ArrayList<>(Arrays.asList(durationField.getText()));
        ArrayList<String> numberFieldNames = new ArrayList<>(Arrays.asList("Duracion"));
        TextField [] textFields = {bluePrintTitleField, lgacField, stateField, directorTextField, coDirectorFIeld,
                modalityField, receptionWorkName, requirementsTextField, studentField};
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
                String invalidDescriptionField = textValidations.validateDescriptionField(descriptionField.getText());
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

