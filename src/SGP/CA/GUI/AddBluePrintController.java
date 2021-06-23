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
import java.util.Date;

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

        public static void main(String[] args) {
            launch(args);
        }
}

