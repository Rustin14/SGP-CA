package SGP.CA.GUI;

import SGP.CA.Domain.BluePrint;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import SGP.CA.DataAccess.BluePrintDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConsultBluePrintController extends Application{

    InvestigationProjectConsultController investigationProjectConsultController;

    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField directorTextField;

    @FXML
    private TextField bluePrintTitleTextField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField associatedLGAC;

    @FXML
    private TextField stateTextField;

    @FXML
    private TextField coDirectorTextField;

    @FXML
    private TextField durationTextField;

    @FXML
    private TextField modalityTextField;

    @FXML
    private TextField receptionWorkNameTextField;

    @FXML
    private TextField requirementsTextField;

    @FXML
    private TextField studentTextField;

    @FXML
    private TextArea descriptionTextArea;

    @Override
    public void start (Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ConsultBluePrintFXML.fxml"));
            primaryStage.setTitle("Registrar anteproyecto");
            primaryStage.setScene(new Scene(root, 900, 600));
        }catch (IOException ioException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        primaryStage.show();
    }

    public void exitButtonEvent() {
        try {
            showExitConsultConfirmation();
        }catch (IOException ioException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }

    }

    public void deleteButtonEvent() {
        BluePrintDAO bluePrintDAO = new BluePrintDAO();
        try {
            int result = bluePrintDAO.deleteBluePrint(bluePrintTitleTextField.getText());
            if (result == 1){
                showSuccessfulDeleteAlert();
            }else{
                showFailedOperationAlert();
            }
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

    public void modifyButtonEvent () {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyBluePrintFXML.fxml"));
            stage.setScene(new Scene(root));
        }catch (IOException ioException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(modifyButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void getBluePrintTitle (InvestigationProjectConsultController stage2Controller, String bluePrintTitle) {
        bluePrintTitleTextField.setText(bluePrintTitle);
        investigationProjectConsultController = stage2Controller;
        searchProject();
    }


    public void searchProject () {
        BluePrintDAO bluePrintDAO = new BluePrintDAO();
        BluePrint bluePrint = new BluePrint();
        try {
            bluePrint = bluePrintDAO.searchBluePrintByTitle(bluePrintTitleTextField.getText());
        }catch (SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String startDate = setDate.format(bluePrint.getStartDate());
        startDateField.setText(startDate);
        associatedLGAC.setText(bluePrint.getAssociatedLgac());
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

    public void showFailedOperationAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/FailedRegisterAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(deleteButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showSuccessfulDeleteAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConfirmationDeleteBluePrintAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(deleteButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showExitConsultConfirmation() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitConsultBluePrintAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(deleteButton.getScene().getWindow());
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
