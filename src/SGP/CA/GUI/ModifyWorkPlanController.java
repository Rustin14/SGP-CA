package SGP.CA.GUI;

import SGP.CA.DataAccess.ObjectiveDAO;
import SGP.CA.DataAccess.WorkPlanDAO;
import SGP.CA.Domain.Objective;
import SGP.CA.Domain.WorkPlan;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ModifyWorkPlanController extends Application {

    ConsultWorkPlanController consultWorkPlanController;

    @FXML
    private Button addObjectiveButton;

    @FXML
    private ComboBox<String> objectivesComboBox;

    @FXML
    private Button modifyComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField workPlanKeyTextField;

    @FXML
    private TextField startDateTextField;

    @FXML
    private TextField endDateTextField;

    @FXML
    private Button addToPlanButton;

    @FXML
    private ComboBox<String> objectivesAddedComboBox;

    private WorkPlan workPlanToModify;

    private ObservableList<String> objectiveTitles = FXCollections.observableArrayList();

    private ArrayList<String> objectivesAddedToPlan = new ArrayList<>();

    private ModifyWorkPlanController modifyWorkPlanController;

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyWorkPlanFXML.fxml"));
            primaryStage.setTitle("Plan de trabajo");
            primaryStage.setScene(new Scene(root, 900, 600));
        }catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        primaryStage.show();
    }

    public void getWorkPlanKey(ConsultWorkPlanController consultWorkPlanController,String workPlanKey) {
        modifyWorkPlanController = this;
        workPlanKeyTextField.setText(workPlanKey);
        this.consultWorkPlanController = consultWorkPlanController;
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        WorkPlan workPlan = new WorkPlan();
        try {
            workPlan = workPlanDAO.searchWorkPlanByWorkPlanKey(workPlanKey);
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String starDate = setDate.format(workPlan.getStartDate().getTime());
        startDateTextField.setText(starDate);
        String endDate = setDate.format(workPlan.getEndingDate().getTime());
        endDateTextField.setText(endDate);
        workPlanToModify = workPlan;
        try {
            searchObjectives();
            searchObjectivesOfCurrentWorkPlan();
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }

    }

    public void searchObjectives() throws SQLException {
        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
        ObservableList<String> objectiveTitlesRegistered = FXCollections.observableArrayList();
        ArrayList<Objective> objectives = objectiveDAO.getAllObjectives();
        for (int i=0; i< objectives.size(); i++) {
            if (!objectiveTitlesRegistered.contains(objectives.get(i).getObjectiveTitle())) {
                objectiveTitlesRegistered.add(objectives.get(i).getObjectiveTitle());
            }
        }
        objectivesComboBox.setItems(objectiveTitlesRegistered);
    }

    public void searchObjectivesOfCurrentWorkPlan() {
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        ArrayList<WorkPlan> workPlans = new ArrayList<>();
        try {
            workPlans = workPlanDAO.getAllWorkPlans();
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        for (int i=0; i<workPlans.size(); i++) {
            if (workPlans.get(i).getWorkPlanKey().equals(workPlanToModify.getWorkPlanKey())){
                if (!workPlans.get(i).getObjective().equals("")) {
                    objectivesAddedToPlan.add(workPlans.get(i).getObjective());
                    objectiveTitles.add(workPlans.get(i).getObjective());
                }
            }
        }
        objectivesAddedComboBox.setItems(objectiveTitles);
    }

    public void exitButtonEvent() {
        try {
            showExitModifyWorkPlanAlert();
        }catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo corectamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
    }

    public void saveButtonEvent() {
        boolean noEmptyTextField = checkEmptyTextFields();
        if (!noEmptyTextField) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String errorMessage = "No has llenado todos los campos";
            alertBuilder.errorAlert(errorMessage);
        }else {
            boolean noExceededLimitText = checkTextLimit();
            if (!noExceededLimitText) {
                AlertBuilder alertBuilder = new AlertBuilder();
                String errorMessage = "Clave de plan de trabajo muy extensa";
                alertBuilder.errorAlert(errorMessage);
            }else {
                boolean validWorkPlanKey = validateWorkPlanKey();
                if (!validWorkPlanKey) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String errorMessage = "La clave solo debe contener letras y/o numeros y/o guion medio o bajo";
                    alertBuilder.errorAlert(errorMessage);
                }else {
                    WorkPlan workPlan = new WorkPlan();
                    WorkPlanDAO workPlanDAO = new WorkPlanDAO();
                    workPlan.setWorkPlanKey(workPlanKeyTextField.getText());
                    DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
                    String startDateString = startDateTextField.getText();
                    try {
                        Date startDate = setDate.parse(startDateString);
                        workPlan.setStartDate(startDate);
                        String endDateString = endDateTextField.getText();
                        Date endDate = setDate.parse(endDateString);
                        workPlan.setEndingDate(endDate);
                    }catch (ParseException exParseException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String errorMessage = "La fecha ingresada no esta en el formato dd/MM/yyyy";
                        alertBuilder.errorAlert(errorMessage);
                    }
                    try {
                        workPlanDAO.deleteWorkPlan(workPlanToModify.getWorkPlanKey());
                    }catch (SQLException exSqlException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                    int resultWorkPlanDAO = 0;
                    try {
                        for (int i=0; i<objectiveTitles.size(); i++) {
                            workPlan.setObjective(objectiveTitles.get(i));
                            resultWorkPlanDAO += workPlanDAO.saveWorkPlan(workPlan);
                        }
                    }catch (SQLException exSqlException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                    try {
                        if (resultWorkPlanDAO == objectivesAddedToPlan.size()) {
                            showSuccessfulUpdateAlert();
                            Stage stage = (Stage) saveButton.getScene().getWindow();
                            stage.close();
                        }else {
                            showFailedRegisterAlert();
                        }
                    }catch (IOException exIoException){
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "No se cargo corectamente el componente del sistema";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                }
            }
        }
    }

    public void addToPlanEvent() {
        String objectiveSelected = objectivesComboBox.getSelectionModel().getSelectedItem();
        if (!objectiveTitles.contains(objectiveSelected)) {
            objectiveTitles.add(objectiveSelected);
            objectivesAddedToPlan.add(objectiveSelected);
            objectivesAddedComboBox.setItems(objectiveTitles);
        }
    }

    public void modifyObjectiveEvent() {
        String objectiveSelected = objectivesComboBox.getSelectionModel().getSelectedItem();
        Stage stage2 = new Stage();
        FXMLLoader loader = new FXMLLoader();
        AnchorPane root = new AnchorPane();
        try {
            root = loader.load(getClass().getResource("FXML/ModifyObjectiveFXML.fxml").openStream());
        }catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo corectamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        ModifyObjectiveController modifyObjectiveController = (ModifyObjectiveController) loader.getController();

        modifyObjectiveController.getObjective(modifyWorkPlanController, objectiveSelected);
        Scene scene = new Scene(root);
        stage2.setScene(scene);
        stage2.alwaysOnTopProperty();
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.showAndWait();
        try {
            searchObjectives();
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
    }

    public void addObjectiveEvent() throws  IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/AddObjectiveFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addObjectiveButton.getScene().getWindow());
        stage.showAndWait();
        try {
            searchObjectives();
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }

    }

    public void showSuccessfulUpdateAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConfirmationModifyWorkPlanAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showExitModifyWorkPlanAlert() throws IOException {
        AlertBuilder alertBuilder = new AlertBuilder();
        boolean confirmationMessage = alertBuilder.confirmationAlert("¿Estas seguro que desea salir?");
        if (confirmationMessage) {
            Stage stagePrincipal = (Stage) exitButton.getScene().getWindow();
            stagePrincipal.close();
        }
    }

    public void showFailedRegisterAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/FailedRegisterAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public boolean checkEmptyTextFields() {
        TextField [] textFields = {workPlanKeyTextField, startDateTextField, endDateTextField};
        for (int i=0; i<textFields.length; i++){
            if (textFields[i].getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkTextLimit() {
        if (workPlanKeyTextField.getText().length() > 10) {
            return false;
        }
        return true;
    }

    public boolean validateWorkPlanKey() {
        String possibleKey = workPlanKeyTextField.getText();
        boolean validKey = false;
        for (int i=0; i<possibleKey.length(); i++){
            if ((possibleKey.charAt(i) >= '0' && possibleKey.charAt(i) >= '9') ||
                    (possibleKey.charAt(i) >= 'A' && possibleKey.charAt(i) <= 'Z') ||
                    (possibleKey.charAt(i) >= 'a' && possibleKey.charAt(i) <= 'z') ||
                    (possibleKey.charAt(i) >= '-' || possibleKey.charAt(i) <= '_')) {
                validKey = true;
            }else {
                return false;
            }
        }
        return validKey;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
