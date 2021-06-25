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
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ConsultWorkPlanFXML.fxml"));
            primaryStage.setTitle("Consultar plan de trabajo");
            primaryStage.setScene(new Scene(root, 1000, 600));
        }catch (IOException ioException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        primaryStage.show();
    }

    @FXML
    public void initialize () {
        consultWorkPlanController = this;
        searchAllWorkPlans();
    }

    public void searchAllWorkPlans () {
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        try {
            workPlans = workPlanDAO.getAllWorkPlans();
        }catch (SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
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
        ObservableList<String> noRepeatedPlansList = FXCollections.observableArrayList();
        for (int i=0; i< workPlanPeriods.size(); i++){
            if (!noRepeatedPlansList.contains(workPlanPeriods.get(i))){
                noRepeatedPlansList.add(workPlanPeriods.get(i));
            }
        }
        workPlanPeriods = noRepeatedPlansList;
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
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo corectamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }catch(SQLException sqlException){
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }catch (NullPointerException nullPointerException){
            workPlanComboBox.getSelectionModel().clearSelection();
            workPlanComboBox.setPromptText("Selecciona un plan");
        }
    }

    public void objectivesComboBoxEvent(){
        String titleSelected = objectivesComboBox.getSelectionModel().getSelectedItem();
        objectiveSelectedButton.setText(titleSelected);
    }

    public void objectiveSelectedEvent() {
        if (objectiveSelectedButton.getText() == ""){
            try {
                showMissingObjectiveAlert();
            }catch (IOException ioException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
        }else{
            Stage stage2 = new Stage();
            FXMLLoader loader = new FXMLLoader();
            try {
                AnchorPane root = loader.load(getClass().getResource("FXML/ConsultObjectiveFXML.fxml").openStream());
                ConsultObjectiveController consultObjectiveController = (ConsultObjectiveController) loader.getController();
                consultObjectiveController.getObjectiveTitle(consultWorkPlanController, objectiveSelectedButton.getText());
                Scene scene = new Scene(root);
                stage2.setScene(scene);
                stage2.alwaysOnTopProperty();
                stage2.initModality(Modality.APPLICATION_MODAL);
                stage2.showAndWait();
            }catch (IOException ioException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
        }
    }

    public void modifyButtonEvent () {
        String selectedOption = workPlanComboBox.getSelectionModel().getSelectedItem();
        int indexSelected = workPlanComboBox.getSelectionModel().getSelectedIndex();
        if (workPlanComboBox.getSelectionModel().getSelectedItem() == null || selectedOption.equals("+Añadir plan de trabajo")){
            try {
                showMissingWorkPlanAlert();
            }catch (IOException ioException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
        }else{
            WorkPlan workPlan = workPlans.get(indexSelected);
            Stage stage2 = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = new AnchorPane();
            try {
                root = loader.load(getClass().getResource("FXML/ModifyWorkPlanFXML.fxml").openStream());
            }catch (IOException ioException){
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
            ModifyWorkPlanController modifyWorkPlanController = loader.getController();
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

    public void consultMembers() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultMember")) {
            try {
                ScreenController.instance.addScreen("consultMember", FXMLLoader.load(getClass().getResource("FXML/ConsultMemberFXML.fxml")));
            } catch (IOException ioException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultMember");
    }

    public void consultEvidences() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultEvidence")) {
            try {
                ScreenController.instance.addScreen("consultEvidence", FXMLLoader.load(getClass().getResource("FXML/ConsultEvidenceResponsibleFXML.fxml")));
            } catch (IOException ioException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultEvidence");
    }

    public void consultEvents() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultEvents")) {
            try {
                ScreenController.instance.addScreen("consultEvents", FXMLLoader.load(getClass().getResource("FXML/ConsultEventsResponsibleFXML.fxml")));
            } catch (IOException ioException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultEvents");
    }

    public void consultProjects() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultProjects")) {
            try {
                ScreenController.instance.addScreen("consultProjects", FXMLLoader.load(getClass().getResource("FXML/InvestigationProjectConsultResponsibleFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultProjects");
    }

    public void goToProfile() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("responsibleProf")) {
            try {
                ScreenController.instance.addScreen("responsibleProf", FXMLLoader.load(getClass().getResource("FXML/ResponsibleProfileFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("responsibleProf");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
