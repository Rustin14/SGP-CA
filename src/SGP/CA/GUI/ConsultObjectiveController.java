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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsultObjectiveController extends Application{

    ConsultWorkPlanController consultWorkPlanController;

    @FXML
    private TextField objectiveTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextArea strategyTextArea;

    @FXML
    private TextArea goalTextArea;

    @FXML
    private TextArea actionTextArea;

    @FXML
    private TextArea resultTextArea;

    @FXML
    private Button closeButton;

    @FXML
    private ComboBox<String> strategiesComboBox;

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ConsultObjectiveFXML.fxml"));
            primaryStage.setTitle("Objetivo");
            primaryStage.setScene(new Scene(root, 700, 400));
        }catch (IOException ioException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        primaryStage.show();
    }

    public void getObjectiveTitle(ConsultWorkPlanController consultWorkPlanController,String objectiveTitle) {
        objectiveTextField.setText(objectiveTitle);
        this.consultWorkPlanController =consultWorkPlanController;
        searchObjective();
    }

    public void searchObjective () {
        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
        Objective objective = new Objective();
        try {
            objective = objectiveDAO.searchObjectiveByTitle(objectiveTextField.getText());
        }catch (SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        descriptionTextArea.setText(objective.getDescription());
        ArrayList<Objective> objectives = new ArrayList<>();
        try {
            objectives = objectiveDAO.getAllObjectives();
        }catch (SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        ObservableList<String> strategies = FXCollections.observableArrayList();
        for (int i=0; i< objectives.size(); i++){
            if (objectives.get(i).getObjectiveTitle().equals(objectiveTextField.getText())){
                strategies.add(objectives.get(i).getStrategy());
            }
        }
        strategiesComboBox.setItems(strategies);
    }

    public void strategiesComboBoxEvent () {
        String strategySelected = strategiesComboBox.getSelectionModel().getSelectedItem();
        StrategyDAO strategyDAO = new StrategyDAO();
        Strategy strategy = new Strategy();
        try {
            strategy = strategyDAO.searchStrategyByStrategy(strategySelected);
        }catch (SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        numberTextField.setText(String.valueOf(strategy.getNumber()));
        strategyTextArea.setText(strategy.getStrategy());
        goalTextArea.setText(strategy.getGoal());
        actionTextArea.setText(strategy.getAction());
        resultTextArea.setText(strategy.getResult());
    }

    public void closeButtonEvent(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
