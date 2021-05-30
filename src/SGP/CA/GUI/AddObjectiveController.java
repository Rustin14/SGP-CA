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
import javafx.stage.Stage;
import SGP.CA.Domain.Objective;
import SGP.CA.Domain.Strategy;
import SGP.CA.DataAccess.ObjectiveDAO;
import SGP.CA.DataAccess.StrategyDAO;

import javafx.event.ActionEvent;
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
    private Button deleteButton;

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
    private Button addButton;

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
    public void cancelButtonEvent(ActionEvent event) {
        System.out.println("Cancel button pressed");
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
    public void saveButtonEvent(ActionEvent event) {
        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
        StrategyDAO strategyDAO = new StrategyDAO();
        if (objectiveTitleTextField.getText() == "" && descriptionTextArea.getText() == ""){
            System.out.println("No se puede registrar falta informacion");
            //TO DO
        }else{
            if (allStrategies.size() == 0){
                System.out.println("Añade por lo menos una estrategia");
                //TO DO
            }else{
                ArrayList<Objective> objectives = new ArrayList<>();
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

    public static void main(String[] args) {
        launch(args);
    }
}
