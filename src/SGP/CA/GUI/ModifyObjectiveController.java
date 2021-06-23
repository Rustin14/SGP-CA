package SGP.CA.GUI;

import SGP.CA.DataAccess.ObjectiveDAO;
import SGP.CA.DataAccess.StrategyDAO;
import SGP.CA.Domain.Objective;
import SGP.CA.Domain.Strategy;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModifyObjectiveController extends Application {

    ModifyWorkPlanController modifyWorkPlanController;

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

    private ArrayList<Strategy> strategies = new ArrayList<>();

    private Objective objectiveToModify;

    private ObservableList<String> strategyTitles = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyObjectiveFXML.fxml"));
            primaryStage.setTitle("Edicion");
            primaryStage.setScene(new Scene(root, 900, 600));
        }catch (IOException ioException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        primaryStage.show();
    }

    public void cancelButtonEvent() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitModifyObjectiveAlertFXML.fxml"));
            stage.setScene(new Scene(root));
        }catch (IOException ioException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(cancelButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void getObjective(ModifyWorkPlanController modifyWorkPlanController, String objectiveTitle) {
        this.modifyWorkPlanController = modifyWorkPlanController;
        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
        Objective objective = new Objective();
        try {
            objective = objectiveDAO.searchObjectiveByTitle(objectiveTitle);
        }catch (SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        objectiveTitleTextField.setText(objective.getObjectiveTitle());
        descriptionTextArea.setText(objective.getDescription());
        objectiveToModify = objective;
        getAllStrategies();
    }

    public void deleteButtonEvent() {
        int indexSelected = strategyComboBox.getSelectionModel().getSelectedIndex();
        if (strategyTitles.size() == 1){
            try {
                showInvalidDeleteStrategyAlert();
            }catch (IOException ioException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
        }else{
            if (indexSelected == 0){
                ObservableList<String> auxiliaryList = FXCollections.observableArrayList();
                ArrayList<Strategy> auxiliaryStrategiesList = new ArrayList<>();
                for (int i=1; i<strategyTitles.size(); i++){
                    auxiliaryList.add(strategyTitles.get(i));
                    auxiliaryStrategiesList.add(strategies.get(i));
                }
                strategyTitles = auxiliaryList;
                strategies = auxiliaryStrategiesList;
            }else{
                strategyTitles.remove(indexSelected);
                strategies.remove(indexSelected);
            }
            strategyComboBox.setItems(strategyTitles);
            displayNumberTextField.clear();
            displayStrategyTextField.clear();
            displayGoalTextField.clear();
            displayActionTextField.clear();
            displayResultTextField.clear();
            strategyComboBox.getSelectionModel().clearSelection();
        }
    }

    public void addButtonEvent(){
        Strategy strategy = new Strategy();
        strategy.setNumber(Integer.parseInt(addNumberTextField.getText()));
        strategy.setStrategy(addStrategyTextField.getText());
        strategy.setGoal(addGoalTextField.getText());
        strategy.setAction(addActionTextField.getText());
        strategy.setResult(addResultTextField.getText());
        if (!strategies.contains(strategy)){
            strategies.add(strategy);
            strategyTitles.add(strategy.getStrategy());
            strategyComboBox.setItems(strategyTitles);
        }
    }

    public void saveButtonEvent() {
        if (objectiveTitleTextField.getText() == "" || descriptionTextArea.getText() == ""){
            try {
                showMissingInformationAlert();
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
            int resultObjectiveDAO = 0;
            int resultStrategyDAO = 0;
            try {
                for (int i=0; i< strategies.size(); i++){
                    strategyDAO.deleteStrategy(strategies.get(i).getStrategy());
                }
            }catch (SQLException sqlException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
            int resultDeleteObjectiveDAO = 0;
            try {
                resultDeleteObjectiveDAO = objectiveDAO.deleteObjective(objectiveToModify.getObjectiveTitle());
            }catch (SQLException sqlException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
            if (resultDeleteObjectiveDAO >= 1){
                try {
                    for (int i=0; i< strategies.size(); i++){
                        objective.setStrategy(strategies.get(i).getStrategy());
                        resultObjectiveDAO += objectiveDAO.saveObjective(objective);
                        resultStrategyDAO += strategyDAO.saveStrategy(strategies.get(i));
                    }
                }catch (SQLException sqlException){
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
                    alertBuilder.exceptionAlert(exceptionMessage);
                }
                if (resultObjectiveDAO == strategies.size() && resultStrategyDAO == strategies.size()){
                    try {
                        showSuccessfulUpdateAlert();
                    }catch (IOException ioException){
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "No se cargo correctamente el componente del sistema";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                }else{
                    try {
                        showFailedRegisterAlert();
                    }catch (IOException ioException){
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "No se cargo correctamente el componente del sistema";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                }
            }else{
                try {
                    showFailedRegisterAlert();
                }catch (IOException ioException){
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String exceptionMessage = "No se cargo correctamente el componente del sistema";
                    alertBuilder.exceptionAlert(exceptionMessage);
                }
            }
        }
    }

    public void strategyComboBoxEvent() {
        String strategySelected = strategyComboBox.getSelectionModel().getSelectedItem();
        int indexSelected = strategyTitles.indexOf(strategySelected);
        Strategy strategy;
        try {
            if (indexSelected == 0) {
                strategy = strategies.get(0);
            } else {
                strategy = strategies.get(indexSelected);
            }
            displayNumberTextField.setText(String.valueOf(strategy.getNumber()));
            displayStrategyTextField.setText(strategy.getStrategy());
            displayGoalTextField.setText(strategy.getGoal());
            displayActionTextField.setText(strategy.getAction());
            displayResultTextField.setText(strategy.getResult());
        }catch (IndexOutOfBoundsException indexOutOfBoundsException){
            strategyComboBox.getSelectionModel().clearSelection();
            displayNumberTextField.clear();
            displayStrategyTextField.clear();
            displayGoalTextField.clear();
            displayActionTextField.clear();
            displayResultTextField.clear();
        }
    }

    public void modifyButtonEvent() {
        Strategy strategy = new Strategy();
        strategy.setNumber(Integer.parseInt(displayNumberTextField.getText()));
        strategy.setStrategy(displayStrategyTextField.getText());
        strategy.setGoal(displayGoalTextField.getText());
        strategy.setAction(displayActionTextField.getText());
        strategy.setResult(displayResultTextField.getText());
        if (!strategies.contains(strategy)) {
            String strategySelected = strategyComboBox.getSelectionModel().getSelectedItem();
            int indexSelected = strategyTitles.indexOf(strategySelected);
            strategies.get(indexSelected).setNumber(strategy.getNumber());
            strategies.get(indexSelected).setStrategy(strategy.getStrategy());
            strategies.get(indexSelected).setGoal(strategy.getGoal());
            strategies.get(indexSelected).setAction(strategy.getAction());
            strategies.get(indexSelected).setResult(strategy.getResult());
            strategyTitles.set(indexSelected, strategy.getStrategy());
            strategyComboBox.setItems(strategyTitles);
        }else{
            try {
                showNoStrategyModifiedAlert();
            }catch (IOException ioException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }

        }
    }

    public void getAllStrategies() {
        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
        StrategyDAO strategyDAO = new StrategyDAO();
        ArrayList<Objective> objectives = new ArrayList<>();
        try {
            objectives = objectiveDAO.getAllObjectives();
        }catch (SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        for (int i=0; i<objectives.size(); i++) {
            if (objectives.get(i).getObjectiveTitle().equals(objectiveTitleTextField.getText())){
                strategyTitles.add(objectives.get(i).getStrategy());
            }
        }
        strategyComboBox.setItems(strategyTitles);
        try {
            for (int i=0; i< strategyTitles.size(); i++){
                Strategy strategy = strategyDAO.searchStrategyByStrategy(strategyTitles.get(i));
                strategies.add(strategy);
            }
        }catch (SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
    }

    public void showSuccessfulUpdateAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/SuccessfulUpdateModifyObjectiveAlertFXML.fxml"));
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

    public void showFailedRegisterAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/FailedRegisterAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showInvalidDeleteStrategyAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/InvalidDeleteStrategyModifyObjectiveAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showNoStrategyModifiedAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/NoStrategyModifiedAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}