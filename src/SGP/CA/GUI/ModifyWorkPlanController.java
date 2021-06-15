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
import javafx.stage.Stage;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
    private ComboBox<String> addObjectivesComboBox;

    @FXML
    private Button addToPlanButton;

    @FXML
    private ComboBox<String> objectivesAddedComboBox;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyWorkPlanFXML.fxml"));
        primaryStage.setTitle("Plan de trabajo");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void getWorkPlanKey(ConsultWorkPlanController consultWorkPlanController,String workPlanKey) throws SQLException, ClassNotFoundException{
        workPlanKeyTextField.setText(workPlanKey);
        this.consultWorkPlanController = consultWorkPlanController;
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        WorkPlan workPlan = workPlanDAO.searchWorkPlanByWorkPlanKey(workPlanKey);
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String starDate = setDate.format(workPlan.getStartDate().getTime());
        startDateTextField.setText(starDate);
        String endDate = setDate.format(workPlan.getEndingDate().getTime());
        endDateTextField.setText(endDate);
        searchObjectives();
    }

    public void searchObjectives() throws SQLException, ClassNotFoundException {
        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
        ArrayList<Objective> objectives = objectiveDAO.getAllObjectives();
        ObservableList<String> objectiveTitles = FXCollections.observableArrayList();
        for (int i=0; i< objectives.size(); i++){
            objectiveTitles.add(objectives.get(i).getObjectiveTitle());
        }
        objectivesComboBox.setItems(objectiveTitles);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
