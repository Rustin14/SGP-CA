package SGP.CA.GUI;

import SGP.CA.DataAccess.WorkPlanDAO;
import SGP.CA.Domain.WorkPlan;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ConsultWorkPlanController extends Application{

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConsultWorkPlanFXML.fxml"));
        primaryStage.setTitle("Consultar plan de trabajo");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        workPlans = workPlanDAO.getAllWorkPlans();
        for(WorkPlan workPlan: workPlans){
            DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
            String starDate = setDate.format(workPlan.getStartDate().getTime());
            String endingDate = setDate.format(workPlan.getEndingDate().getTime());
            String period = "Plan de trabajo [";
            period+= starDate.substring(6);
            period+= "-";
            period+= endingDate.substring(6);
            period+= "]";
            workPlanPeriods.add(period);
        }
        workPlanPeriods.add("+Añadir plan de trabajo");
        workPlanComboBox.setItems(workPlanPeriods);
    }

    public void workPlanComboBoxEvent() throws IOException {
        String selectedOption = workPlanComboBox.getSelectionModel().getSelectedItem();
        if (selectedOption == "+Añadir plan de trabajo"){
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXML/AddWorkPlanFXML.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(workPlanComboBox.getScene().getWindow());
            stage.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
