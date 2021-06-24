package SGP.CA.GUI;

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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddBluePrintController extends Application{

    @FXML
    private Button saveButton;

    @FXML
    private Button exitButton;

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
        public void start(Stage primaryStage){
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

        public void exitButtonEvent () {
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitSaveProjectAlertFXML.fxml"));
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(exitButton.getScene().getWindow());
                stage.showAndWait();

                Stage stagePrincipal = (Stage) saveButton.getScene().getWindow();
                stagePrincipal.close();
            }catch (IOException ioException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
        }

        public void saveButtonEvent () {
            boolean noEmptyTextFields = checkEmptyTextFields();
            if (!noEmptyTextFields){
                AlertBuilder alertBuilder = new AlertBuilder();
                String errorMessage = "No has llenado todos los campos";
                alertBuilder.errorAlert(errorMessage);
            }else{
                boolean noExceededLimitSize = checkTextLimit();
                if (!noExceededLimitSize){
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String errorMessage = "Limite de texto excedido en algun campo";
                    alertBuilder.errorAlert(errorMessage);
                }else{
                    boolean validDurationField = validateDurationField();
                    if (!validDurationField){
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String errorMessage = "Debes ingresar solo numeros en el campo de texto de duracion";
                        alertBuilder.errorAlert(errorMessage);
                    }else{
                        boolean validStringTextFields = validateStringTextFields();
                        if (!validStringTextFields){
                            AlertBuilder alertBuilder = new AlertBuilder();
                            String errorMessage = "Solo debes ingresar letras en los campos que no sean duracion y fecha de inicio";
                            alertBuilder.errorAlert(errorMessage);
                        }else{
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
                                if (action == 1){
                                    showConfirmationAlert();
                                    Stage stagePrincipal = (Stage) saveButton.getScene().getWindow();
                                    stagePrincipal.close();
                                }else{
                                    showFailedRegisterAlert();
                                }
                            }catch (ParseException parseException){
                                AlertBuilder alertBuilder = new AlertBuilder();
                                String errorMessage = "La fecha ingresada no esta en el formato dd/MM/yyyy";
                                alertBuilder.errorAlert(errorMessage);
                            }catch (SQLException sqlException){
                                AlertBuilder alertBuilder = new AlertBuilder();
                                String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
                                alertBuilder.exceptionAlert(exceptionMessage);
                            }catch (IOException ioException){
                                AlertBuilder alertBuilder = new AlertBuilder();
                                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                                alertBuilder.exceptionAlert(exceptionMessage);
                            }
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

    public void showFailedRegisterAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/FailedRegisterAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public boolean checkEmptyTextFields() {
        List<TextField> textFields = Arrays.asList(bluePrintTitleField, startDateField,
                lgacField, stateField, directorTextField, coDirectorFIeld, durationField,
                modalityField, receptionWorkName, requirementsTextField, studentField);
        for (TextField field : textFields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        if (descriptionField.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean checkTextLimit(){
        int [] limitTextSizes = {255, 255, 50, 50, 30,50, 60, 120, 120};
        TextField [] textFields = {bluePrintTitleField, lgacField,
                stateField, coDirectorFIeld, modalityField, studentField, directorTextField,
                receptionWorkName, requirementsTextField};
        for (int i=0; i<textFields.length; i++){
            if (textFields[i].getText().length() > limitTextSizes[i]){
                return false;
            }
        }
        if (descriptionField.getText().length() > 255){
            return false;
        }
        return true;
    }

    public boolean validateDurationField(){
        return durationField.getText().matches("[0-9]*");
    }

    public boolean validateStringTextFields (){
        TextField [] textFields = {bluePrintTitleField, lgacField,
                stateField, coDirectorFIeld, modalityField, studentField, directorTextField,
                receptionWorkName, requirementsTextField};
        for(int i=0; i< textFields.length; i++){
            if (!textFields[i].getText().matches("^[a-zA-Z\\s]*$")){
                return false;
            }
        }
        if (!descriptionField.getText().matches("[a-zA-Z\\s]*$")){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

