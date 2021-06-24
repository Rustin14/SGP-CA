package SGP.CA.GUI;

import javafx.application.Application;
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
import SGP.CA.Domain.Objective;
import SGP.CA.Domain.Strategy;
import SGP.CA.DataAccess.ObjectiveDAO;
import SGP.CA.DataAccess.StrategyDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddObjectiveController extends Application{

    @FXML
    private TextField objectiveTitleTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox<String> strategyComboBox;

    @FXML
    private TextField displayNumberTextField;

    @FXML
    private TextField displayStrategyTextField;

    @FXML
    private TextField displayGoalTextField;

    @FXML
    private TextField displayActionTextField;

    @FXML
    private TextField displayResultTextField;

    @FXML
    private TextField addNumberTextField;

    @FXML
    private TextField addStrategyTextField;

    @FXML
    private TextField addGoalTextField;

    @FXML
    private TextField addActionTextField;

    @FXML
    private TextField addResultTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    private ArrayList<Strategy> allStrategies = new ArrayList<>();
    private ObservableList<String> allStrategiesTitles = FXCollections.observableArrayList();

    @Override
    public void start (Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/AddObjectiveFXML.fxml"));
            primaryStage.setTitle("añadir objetivo ");
            primaryStage.setScene(new Scene(root, 900, 600));
        }catch (IOException ioException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        primaryStage.show();
    }

    @FXML
    public void addButtonEvent() {
        boolean noEmptyTextFields = checkEmptyStrategyTextFields();
        if (!noEmptyTextFields){
            AlertBuilder alertBuilder = new AlertBuilder();
            String errorMessage = "debes llenar los campos antes de añadir una estrategia";
            alertBuilder.errorAlert(errorMessage);
        }else{
            boolean noExceededLimits = checkStrategyTextLimit();
            if (!noExceededLimits){
                AlertBuilder alertBuilder = new AlertBuilder();
                String errorMessage = "Alguno de los campos excede el limite de texto";
                alertBuilder.errorAlert(errorMessage);
            }else{
                boolean validStrategyTextFields = valideStrategyTextFields();
                if (!validStrategyTextFields){
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String errorMessage = "Solo el campo de numero requiere numero los demas solo letras";
                    alertBuilder.errorAlert(errorMessage);
                }else {
                    Strategy strategy = new Strategy();
                    strategy.setNumber(Integer.parseInt(addNumberTextField.getText()));
                    strategy.setStrategy(addStrategyTextField.getText());
                    strategy.setGoal(addGoalTextField.getText());
                    strategy.setAction(addActionTextField.getText());
                    strategy.setResult(addResultTextField.getText());
                    if (!allStrategies.contains(strategy)){
                        allStrategies.add(strategy);
                        allStrategiesTitles.add(strategy.getStrategy());
                        strategyComboBox.setItems(allStrategiesTitles);
                    }
                }
            }
        }
    }

    @FXML
    public void cancelButtonEvent() {
        AlertBuilder alertBuilder = new AlertBuilder();
        boolean confirmationMessage = alertBuilder.confirmationAlert("¿Estas seguro que desea salir?");
        if (confirmationMessage){
            Stage stagePrincipal = (Stage) cancelButton.getScene().getWindow();
            stagePrincipal.close();
        }
    }

    @FXML
    public void deleteButtonEvent () {
        if (strategyComboBox.getSelectionModel().isEmpty()){
            try {
                showNoStrategiesAlert();
            }catch (IOException ioException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
        }else{
            if (allStrategiesTitles.size() == 1){
                try {
                    showNoStrategiesAlert();
                }catch (IOException ioException){
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String exceptionMessage = "No se cargo correctamente el componente del sistema";
                    alertBuilder.exceptionAlert(exceptionMessage);
                }
            }else{
                int indexSelected = strategyComboBox.getSelectionModel().getSelectedIndex();
                if (indexSelected == 0){
                    ObservableList<String> auxiliaryList = FXCollections.observableArrayList();
                    ArrayList<Strategy> auxiliaryStrategiesList = new ArrayList<>();
                    for (int i=1; i<allStrategiesTitles.size(); i++){
                        auxiliaryList.add(allStrategiesTitles.get(i));
                        auxiliaryStrategiesList.add(allStrategies.get(i));
                    }
                    allStrategiesTitles = auxiliaryList;
                    allStrategies = auxiliaryStrategiesList;
                }else{
                    allStrategies.remove(indexSelected);
                    allStrategiesTitles.remove(indexSelected);
                }
                strategyComboBox.setItems(allStrategiesTitles);
                strategyComboBox.getSelectionModel().clearSelection();
                displayNumberTextField.clear();
                displayStrategyTextField.clear();
                displayGoalTextField.clear();
                displayActionTextField.clear();
                displayResultTextField.clear();
            }
        }
    }

    @FXML
    public void saveButtonEvent () {
        if (objectiveTitleTextField.getText().isEmpty() || descriptionTextArea.getText().isEmpty()){
            try {
                showMissingInformationAlert();
            }catch (IOException ioException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
        }else{
            boolean noExceededLimitText = checkObjectiveTextLimit();
            if (!noExceededLimitText){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "El objetivo o la descripcion es demasiado largo";
                alertBuilder.exceptionAlert(exceptionMessage);
            }else{
                boolean validObjectiveTextFields = validateObjectiveTextFields();
                if (!validObjectiveTextFields) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String exceptionMessage = "Solo debes ingresar letras en el titulo y la descripcion";
                    alertBuilder.exceptionAlert(exceptionMessage);
                }else{
                    if (allStrategies.size() == 0){
                        try {
                            showMissingStrategiesAlert();
                        }catch (IOException ioException){
                            AlertBuilder alertBuilder = new AlertBuilder();
                            String exceptionMessage = "No se cargo correctamente el componente del sistema";
                            alertBuilder.exceptionAlert(exceptionMessage);
                        }
                    }else{
                        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
                        StrategyDAO strategyDAO = new StrategyDAO();
                        Objective objective = new Objective();
                        objective.setObjectiveTitle(objectiveTitleTextField.getText());
                        objective.setDescription(descriptionTextArea.getText());
                        int result1 = 0;
                        int result2 = 0;
                        try {
                            for (int i = 0; i < allStrategies.size(); i++){
                                objective.setStrategy(allStrategies.get(i).getStrategy());
                                result1 += objectiveDAO.saveObjective(objective);
                                result2 += strategyDAO.saveStrategy(allStrategies.get(i));
                            }
                            if (result1 == allStrategies.size() && result2 == allStrategies.size()){
                                showAddedObjectiveAlert();
                                Stage stagePrincipal = (Stage) saveButton.getScene().getWindow();
                                stagePrincipal.close();
                            }else{
                                showFailedRegisterAlert();
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
                }
            }
        }
    }

    @FXML
    public void strategyComboBoxEvent() {
        String strategySelected = strategyComboBox.getSelectionModel().getSelectedItem();
        int indexSelected = allStrategiesTitles.indexOf(strategySelected);
        Strategy strategy;
        if (indexSelected == 0){
            strategy = allStrategies.get(0);
        }else{
            int index = 0;
            for (int i=0; i<allStrategies.size(); i++){
                if (allStrategies.get(i).getStrategy().equals(strategySelected)){
                    index = i;
                    break;
                }
            }
            strategy = allStrategies.get(index);
        }
        displayNumberTextField.setText(String.valueOf(strategy.getNumber()));
        displayStrategyTextField.setText(strategy.getStrategy());
        displayGoalTextField.setText(strategy.getGoal());
        displayActionTextField.setText(strategy.getAction());
        displayResultTextField.setText(strategy.getResult());
    }

    public void showAddedObjectiveAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ObjectiveAddedAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showMissingInformationAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ObjectiveMissingInformationAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showMissingStrategiesAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ObjectiveMissingStrategiesAlertFXML.fxml"));
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

    public void showNoStrategiesAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/NoStrategiesAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public boolean checkObjectiveTextLimit(){
        if (objectiveTitleTextField.getText().length() > 100){
            return false;
        }
        if (descriptionTextArea.getText().length() > 255){
            return false;
        }
        return true;
    }

    public boolean validateObjectiveTextFields () {
        if (!objectiveTitleTextField.getText().matches("[a-zA-Z\\s]*$")) {
            return false;
        }
        if (!descriptionTextArea.getText().matches("[a-zA-Z\\s]*$")) {
            return false;
        }
        return true;
    }

    public boolean valideStrategyTextFields(){
        TextField [] textFields = {addStrategyTextField, addGoalTextField,
            addActionTextField, addResultTextField};
        for(int i=0; i< textFields.length; i++){
            if (!textFields[i].getText().matches("[a-zA-Z\\s]*$")){
                return false;
            }
        }
        if (!addNumberTextField.getText().matches("[0-9]*")){
            return false;
        }
        return true;
    }

    public boolean checkStrategyTextLimit(){
        int [] limitTextSizes = {255, 100, 255, 255};
        TextField [] textFields = {addStrategyTextField, addGoalTextField, addActionTextField, addResultTextField};
        for (int i=0; i<textFields.length; i++){
            if (textFields[i].getText().length() > limitTextSizes[i]){
                return false;
            }
        }
        return true;
    }

    public boolean checkEmptyStrategyTextFields (){
        TextField [] textFields = {addStrategyTextField, addGoalTextField, addActionTextField, addResultTextField};
        for (int i=0; i<textFields.length; i++){
            if (textFields[i].getText().isEmpty()){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
