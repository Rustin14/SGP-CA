package SGP.CA.GUI;

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
import java.util.Date;

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
        }catch (IOException ioException){
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
        }catch (SQLException sqlException){
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
        }catch (IOException ioException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }

    }

    public void saveButtonEvent() {
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String titleSelected = bluePrintsComboBox.getSelectionModel().getSelectedItem();
        BluePrint bluePrint = new BluePrint();
        bluePrint.setBluePrintTitle(bluePrintTitleTextField.getText());
        String startDateString = starDateTextField.getText();
        Date startDate = new Date();
        try {
            startDate = setDate.parse(startDateString);
        }catch (ParseException parseException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String errorMessage = "La fecha ingresada no esta en el formato dd/MM/yyyy";
            alertBuilder.errorAlert(errorMessage);
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
        }catch (SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        try {
            if (rowsAffectedBluePrintDAO == 1){
                showSuccessfulModifyConfirmationAlert();
            }else{
                showFailedOperationAlert();
            }
        }catch (IOException ioException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }

    }

    @FXML
    public void initialize() {
        try {
            bluePrints = bluePrintDAO.getAllBluePrints();
        }catch (SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        for (BluePrint blueprint : bluePrints){
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

    public void showSuccessfulModifyConfirmationAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConfirmationModifyBluePrintAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showExitAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitModifyBluePrintAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(cancelButton.getScene().getWindow());
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
