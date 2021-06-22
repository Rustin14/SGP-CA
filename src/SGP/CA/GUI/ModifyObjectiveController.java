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
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyObjectiveFXML.fxml"));
        primaryStage.setTitle("Edicion");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void cancelButtonEvent() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitModifyObjectiveAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(cancelButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void getObjective(ModifyWorkPlanController modifyWorkPlanController, String objectiveTitle) throws SQLException{
        this.modifyWorkPlanController = modifyWorkPlanController;
        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
        Objective objective = objectiveDAO.searchObjectiveByTitle(objectiveTitle);
        objectiveTitleTextField.setText(objective.getObjectiveTitle());
        descriptionTextArea.setText(objective.getDescription());
        objectiveToModify = objective;
        getAllStrategies();
    }

    public void deleteButtonEvent() throws IOException{
        int indexSelected = strategyComboBox.getSelectionModel().getSelectedIndex();
        if (strategyTitles.size() == 1){
            showInvalidDeleteStrategyAlert();
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
        strategies.add(strategy);
        strategyTitles.add(strategy.getStrategy());
        strategyComboBox.setItems(strategyTitles);
    }

    public void saveButtonEvent() throws IOException, SQLException{
        if (objectiveTitleTextField.getText() == "" || descriptionTextArea.getText() == ""){
            showMissingInformationAlert();
        }else{
            ObjectiveDAO objectiveDAO = new ObjectiveDAO();
            StrategyDAO strategyDAO = new StrategyDAO();
            Objective objective = new Objective();
            objective.setObjectiveTitle(objectiveTitleTextField.getText());
            objective.setDescription(descriptionTextArea.getText());
            int resultObjectiveDAO = 0;
            int resultStrategyDAO = 0;
            for (int i=0; i< strategies.size(); i++){
                strategyDAO.deleteStrategy(strategies.get(i).getStrategy());
            }
            int resultDeleteObjectiveDAO = objectiveDAO.deleteObjective(objectiveToModify.getObjectiveTitle());
            if (resultDeleteObjectiveDAO >= 1){
                for (int i=0; i< strategies.size(); i++){
                    objective.setStrategy(strategies.get(i).getStrategy());
                    resultObjectiveDAO += objectiveDAO.saveObjective(objective);
                    resultStrategyDAO += strategyDAO.saveStrategy(strategies.get(i));
                }
                if (resultObjectiveDAO == strategies.size() && resultStrategyDAO == strategies.size()){
                    showSuccessfulUpdateAlert();
                }else{
                    showFailedRegisterAlert();
                }
            }else{
                showFailedRegisterAlert();
            }
        }
    }

    public void strategyComboBoxEvent() {
        String strategySelected = strategyComboBox.getSelectionModel().getSelectedItem();
        int indexSelected = strategyTitles.indexOf(strategySelected);
        Strategy strategy;
        if (indexSelected == 0){
            strategy = strategies.get(0);
        }else{
            /*int index = 0;
            for (int i=0; i< strategies.size(); i++){
                if (strategies.get(i).getStrategy().equals(strategySelected)){
                    index = i;
                    break;
                }
            }
            strategy = strategies.get(index);*/
            strategy = strategies.get(indexSelected);
        }
        displayNumberTextField.setText(String.valueOf(strategy.getNumber()));
        displayStrategyTextField.setText(strategy.getStrategy());
        displayGoalTextField.setText(strategy.getGoal());
        displayActionTextField.setText(strategy.getAction());
        displayResultTextField.setText(strategy.getResult());
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
            //TODO
            System.out.println("No has modificado la estrategia");
        }
    }

    public void getAllStrategies() throws SQLException{
        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
        StrategyDAO strategyDAO = new StrategyDAO();
        ArrayList<Objective> objectives = objectiveDAO.getAllObjectives();
        for (int i=0; i<objectives.size(); i++) {
            if (objectives.get(i).getObjectiveTitle().equals(objectiveTitleTextField.getText())){
                strategyTitles.add(objectives.get(i).getStrategy());
            }
        }
        strategyComboBox.setItems(strategyTitles);
        for (int i=0; i< strategyTitles.size(); i++){
            Strategy strategy = strategyDAO.searchStrategyByStrategy(strategyTitles.get(i));
            strategies.add(strategy);
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

    public static void main(String[] args) {
        launch(args);
    }

}