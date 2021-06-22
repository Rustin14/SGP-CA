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

public class ModifyWorkPlanController extends Application{

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
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyWorkPlanFXML.fxml"));
        primaryStage.setTitle("Plan de trabajo");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void getWorkPlanKey(ConsultWorkPlanController consultWorkPlanController,String workPlanKey) throws SQLException, ClassNotFoundException{
        modifyWorkPlanController = this;
        workPlanKeyTextField.setText(workPlanKey);
        this.consultWorkPlanController = consultWorkPlanController;
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        WorkPlan workPlan = workPlanDAO.searchWorkPlanByWorkPlanKey(workPlanKey);
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String starDate = setDate.format(workPlan.getStartDate().getTime());
        startDateTextField.setText(starDate);
        String endDate = setDate.format(workPlan.getEndingDate().getTime());
        endDateTextField.setText(endDate);
        workPlanToModify = workPlan;
        searchObjectives();
        searchObjectivesOfCurrentWorkPlan();
    }

    public void searchObjectives() throws SQLException{
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

    public void searchObjectivesOfCurrentWorkPlan() throws SQLException{
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        ArrayList<WorkPlan> workPlans = workPlanDAO.getAllWorkPlans();
        for (int i=0; i<workPlans.size(); i++){
            if (workPlans.get(i).getWorkPlanKey().equals(workPlanToModify.getWorkPlanKey())){
                if (!workPlans.get(i).getObjective().equals("")){
                    objectivesAddedToPlan.add(workPlans.get(i).getObjective());
                    objectiveTitles.add(workPlans.get(i).getObjective());
                }
            }
        }
        objectivesAddedComboBox.setItems(objectiveTitles);
    }

    public void exitButtonEvent() throws IOException {
        showExitModifyWorkPlanAlert();
    }

    public void saveButtonEvent() throws IOException, ParseException, SQLException, ClassNotFoundException {
        WorkPlan workPlan = new WorkPlan();
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        workPlan.setWorkPlanKey(workPlanKeyTextField.getText());
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String startDateString = startDateTextField.getText();
        Date startDate = setDate.parse(startDateString);
        workPlan.setStartDate(startDate);
        String endDateString = endDateTextField.getText();
        Date endDate = setDate.parse(endDateString);
        workPlan.setEndingDate(endDate);
        workPlanDAO.deleteWorkPlan(workPlanToModify.getWorkPlanKey());
        int resultWorkPlanDAO = 0;
        for (int i=0; i<objectiveTitles.size(); ){
            workPlan.setObjective(objectiveTitles.get(i));
            resultWorkPlanDAO += workPlanDAO.saveWorkPlan(workPlan);
        }
        if (resultWorkPlanDAO == objectivesAddedToPlan.size()){
            showSuccessfulUpdateAlert();
        }else{
            showFailedRegisterAlert();
        }

    }

    public void addToPlanEvent(){
        String objectiveSelected = objectivesComboBox.getSelectionModel().getSelectedItem();
        if (!objectiveTitles.contains(objectiveSelected)){
            objectiveTitles.add(objectiveSelected);
            objectivesAddedToPlan.add(objectiveSelected);
            objectivesAddedComboBox.setItems(objectiveTitles);
        }
    }

    public void modifyObjectiveEvent() throws IOException, SQLException, ClassNotFoundException {
        String objectiveSelected = objectivesComboBox.getSelectionModel().getSelectedItem();
        Stage stage2 = new Stage();
        FXMLLoader loader = new FXMLLoader();
        AnchorPane root = (AnchorPane) loader.load(getClass().getResource("FXML/ModifyObjectiveFXML.fxml").openStream());
        ModifyObjectiveController modifyObjectiveController = (ModifyObjectiveController) loader.getController();
        modifyObjectiveController.getObjective(modifyWorkPlanController, objectiveSelected);
        Scene scene = new Scene(root);
        stage2.setScene(scene);
        stage2.alwaysOnTopProperty();
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.showAndWait();
        searchObjectives();
    }

    public void addObjectiveEvent() throws  IOException, SQLException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/AddObjectiveFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addObjectiveButton.getScene().getWindow());
        stage.showAndWait();
        searchObjectives();
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
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitModifyWorkPlanAlertFXML.fxml"));
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
