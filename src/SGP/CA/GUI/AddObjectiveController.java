package SGP.CA.GUI;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.event.ActionEvent;
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

    private ArrayList<String> allStrategyTitles = new ArrayList<>();
    private ArrayList<Strategy> allStrategies = new ArrayList<>();
    private ObservableList<String> allStrategiesTitles = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/AddObjectiveFXML.fxml"));
        primaryStage.setTitle("añadir objetivo ");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    @FXML
    public void addButtonEvent(ActionEvent event) {
        Strategy strategy = new Strategy();
        strategy.setNumber(Integer.parseInt(addNumberTextField.getText()));
        strategy.setStrategy(addStrategyTextField.getText());
        strategy.setGoal(addGoalTextField.getText());
        strategy.setAction(addActionTextField.getText());
        strategy.setResult(addResultTextField.getText());
        allStrategyTitles.add(strategy.getStrategy());
        allStrategies.add(strategy);
        allStrategiesTitles.add(strategy.getStrategy());
        strategyComboBox.setItems(allStrategiesTitles);
    }

    @FXML
    public void cancelButtonEvent(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/exitAddObjectiveAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(cancelButton.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    public void deleteButtonEvent(ActionEvent event) {
        String strategySelected = strategyComboBox.getSelectionModel().getSelectedItem();
        int indexSelected = allStrategyTitles.indexOf(strategySelected);
        allStrategies.remove(indexSelected);
        allStrategyTitles.remove(indexSelected);
        allStrategiesTitles.remove(indexSelected);
        strategyComboBox.setItems(allStrategiesTitles);
        displayNumberTextField.clear();
        displayStrategyTextField.clear();
        displayGoalTextField.clear();
        displayActionTextField.clear();
        displayResultTextField.clear();
    }

    @FXML
    public void saveButtonEvent(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if (objectiveTitleTextField.getText() == "" || descriptionTextArea.getText() == ""){
            showMissingInformationAlert();
        }else{
            if (allStrategies.size() == 0){
                showMissingStrategiesAlert();
            }else{
                ObjectiveDAO objectiveDAO = new ObjectiveDAO();
                StrategyDAO strategyDAO = new StrategyDAO();
                Objective objective = new Objective();
                objective.setObjectiveTitle(objectiveTitleTextField.getText());
                objective.setDescription(descriptionTextArea.getText());
                int result1 = 0;
                int result2 = 0;
                for (int i = 0; i < allStrategies.size(); i++){
                    objective.setStrategy(allStrategies.get(i).getStrategy());
                    result1 += objectiveDAO.saveObjective(objective);
                    result2 += strategyDAO.saveStrategy(allStrategies.get(i));
                }
                if (result1 == allStrategies.size() && result2 == allStrategies.size()){
                    showAddedObjectiveAlert();
                }else{
                    showFailedRegisterAlert();
                }
            }
        }
    }

    @FXML
    public void strategyComboBoxEvent(ActionEvent event) {
        String strategySelected = strategyComboBox.getSelectionModel().getSelectedItem();
        int indexSelected = allStrategyTitles.indexOf(strategySelected);
        Strategy strategy = allStrategies.get(indexSelected);

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

    public static void main(String[] args) {
        launch(args);
    }
}
