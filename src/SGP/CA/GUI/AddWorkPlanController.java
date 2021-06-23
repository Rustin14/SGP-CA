package SGP.CA.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import SGP.CA.Domain.WorkPlan;
import SGP.CA.DataAccess.WorkPlanDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWorkPlanController extends Application {

    @FXML
    private TextField workPlanKeyTextField;

    @FXML
    private TextField startDateTextField;

    @FXML
    private TextField endDateTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/AddWorkPlanFXML.fxml"));
        primaryStage.setTitle("Registrar plan de trabajo");
        primaryStage.setScene(new Scene(root, 600, 200));
        primaryStage.show();
    }

    public void cancelButtonEvent() throws IOException {
        showExitRegisterWorkPlanAlert();
    }

    public void saveButtonEvent() throws ParseException, SQLException, IOException{
        WorkPlan workPlan = new WorkPlan();
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        workPlan.setWorkPlanKey(workPlanKeyTextField.getText());
        String stringStartDate = startDateTextField.getText();
        Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringStartDate);
        workPlan.setStartDate(startDate);
        String stringEndDate = endDateTextField.getText();
        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringEndDate);
        workPlan.setEndingDate(endDate);
        int rowsAffectedWorkPlanDAO = workPlanDAO.saveWorkPlan(workPlan);
        if (rowsAffectedWorkPlanDAO == 1){
            showConfirmationRegisterAlert();
        }else{
            showFailedOperationAlert();
        }
    }

    public void showFailedOperationAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/FailedRegisterAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showExitRegisterWorkPlanAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitAddBluePrintAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showConfirmationRegisterAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConfirmationRegisterWorkPlanAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
