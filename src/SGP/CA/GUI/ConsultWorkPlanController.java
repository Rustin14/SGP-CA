package SGP.CA.GUI;

import SGP.CA.DataAccess.WorkPlanDAO;
import SGP.CA.Domain.Member;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ConsultWorkPlanController extends Application{

    @FXML
    private ComboBox<String> objectivesComboBox;

    @FXML
    private Button objectiveSelectedButton;

    @FXML
    private ComboBox<String> workPlanComboBox;

    @FXML
    private TextField academicBodyTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField chargeTextField;

    private ObservableList<String> workPlanPeriods = FXCollections.observableArrayList();

    private ArrayList<WorkPlan> workPlans = new ArrayList<>();

    private ObservableList<String> objectiveTitles = FXCollections.observableArrayList();

    ConsultWorkPlanController consultWorkPlanController;

    Member member = Member.selectedMember;

    @Override
    public void start(Stage primaryStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConsultWorkPlanFXML.fxml"));
        primaryStage.setTitle("Consultar plan de trabajo");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    @FXML
    public void initialize() throws SQLException{
        consultWorkPlanController = this;
        searchAllWorkPlans();
    }

    public void searchAllWorkPlans() throws SQLException{
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        workPlans = workPlanDAO.getAllWorkPlans();
        ArrayList<WorkPlan>auxWorkPlans = new ArrayList<>();
        for(WorkPlan workPlan: workPlans){
            if (!auxWorkPlans.contains(workPlan)){
                DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
                String starDate = setDate.format(workPlan.getStartDate().getTime());
                String endingDate = setDate.format(workPlan.getEndingDate().getTime());
                String period = "Plan de trabajo [";
                period+= starDate.substring(6);
                period+= "-";
                period+= endingDate.substring(6);
                period+= "]";
                workPlanPeriods.add(period);
                auxWorkPlans.add(workPlan);
            }
        }
        workPlanPeriods.add("+Añadir plan de trabajo");
        workPlanComboBox.setItems(workPlanPeriods);
    }

    public void workPlanComboBoxEvent(){
        String selectedOption = workPlanComboBox.getSelectionModel().getSelectedItem();
        try {
            if (selectedOption.equals("+Añadir plan de trabajo")) {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("FXML/AddWorkPlanFXML.fxml"));
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(workPlanComboBox.getScene().getWindow());
                stage.showAndWait();
            } else {
                objectivesComboBox.getItems().clear();
                objectiveSelectedButton.setText("");
                int selectedIndex = workPlanComboBox.getSelectionModel().getSelectedIndex();
                WorkPlanDAO workPlanDAO = new WorkPlanDAO();
                ArrayList<WorkPlan> workPlans = workPlanDAO.getAllWorkPlans();
                for (int i = 0; i < workPlans.size(); i++) {
                    if (workPlans.get(i).getWorkPlanKey().equals(this.workPlans.get(selectedIndex).getWorkPlanKey())) {
                        objectiveTitles.add(workPlans.get(i).getObjective());
                    }
                }
                objectivesComboBox.setItems(objectiveTitles);
            }
        }catch (IOException ioException){
            //TODO
            System.out.println("Excepcion atrapada");
        }catch(SQLException sqlException){
            //TODO
            System.out.println("Excepcion atrapada");
        }catch (NullPointerException nullPointerException){
            workPlanComboBox.getSelectionModel().clearSelection();
            workPlanComboBox.setPromptText("Selecciona un plan");
        }
    }

    public void objectivesComboBoxEvent(){
        String titleSelected = objectivesComboBox.getSelectionModel().getSelectedItem();
        objectiveSelectedButton.setText(titleSelected);
    }

    public void objectiveSelectedEvent() throws IOException, SQLException{
        if (objectiveSelectedButton.getText() == ""){
            showMissingObjectiveAlert();
        }else{
            Stage stage2 = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = (AnchorPane) loader.load(getClass().getResource("FXML/ConsultObjectiveFXML.fxml").openStream());
            ConsultObjectiveController consultObjectiveController = (ConsultObjectiveController) loader.getController();
            consultObjectiveController.getObjectiveTitle(consultWorkPlanController, objectiveSelectedButton.getText());
            Scene scene = new Scene(root);
            stage2.setScene(scene);
            stage2.alwaysOnTopProperty();
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.showAndWait();
        }
    }

    public void modifyButtonEvent() throws IOException, SQLException, ClassNotFoundException{
        String selectedOption = workPlanComboBox.getSelectionModel().getSelectedItem();
        int indexSelected = workPlanComboBox.getSelectionModel().getSelectedIndex();
        if (workPlanComboBox.getSelectionModel().getSelectedItem() == null || selectedOption.equals("+Añadir plan de trabajo")){
            showMissingWorkPlanAlert();
        }else{
            WorkPlan workPlan = workPlans.get(indexSelected);
            Stage stage2 = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = (AnchorPane) loader.load(getClass().getResource("FXML/ModifyWorkPlanFXML.fxml").openStream());
            ModifyWorkPlanController modifyWorkPlanController = (ModifyWorkPlanController) loader.getController();
            modifyWorkPlanController.getWorkPlanKey(consultWorkPlanController, workPlan.getWorkPlanKey());
            Scene scene = new Scene(root);
            stage2.setScene(scene);
            stage2.alwaysOnTopProperty();
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.showAndWait();
            workPlanPeriods.clear();
            searchAllWorkPlans();
        }
    }

    public void showMissingObjectiveAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/MissingObjectiveConsultWorkPlanAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(objectiveSelectedButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showMissingWorkPlanAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/MissingWorkPlanConsultWorkPlanAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(objectiveSelectedButton.getScene().getWindow());
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
