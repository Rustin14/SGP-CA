package SGP.CA.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Application;
import SGP.CA.Domain.InvestigationProject;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import SGP.CA.DataAccess.InvestigationProjectDAO;

public class RegisterInvestigationProjectController extends Application {

    @FXML
    private TextField projectTitleField;

    @FXML
    private TextField endDateField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField lgacField;

    @FXML
    private TextField projectManagerField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField participantsField;

    @FXML
    private Button saveButton;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/RegisterInvestigationProjectFXML.fxml"));
        primaryStage.setTitle("Registrar proyecto");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void saveButtonEvent (ActionEvent event) throws ParseException, SQLException, ClassNotFoundException, IOException {
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setProjectTitle(projectTitleField.getText());
        String endDate = endDateField.getText();
        Date estimateEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
        investigationProject.setEstimatedEndDate(estimateEndDate);
        String stringStartDate = startDateField.getText();
        Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringStartDate);
        investigationProject.setStartDate(startDate);
        investigationProject.setAssociatedLgac(lgacField.getText());
        investigationProject.setParticipants(participantsField.getText());
        investigationProject.setProjectManager(projectManagerField.getText());
        investigationProject.setDescription(descriptionField.getText());
        int action = investigationProjectDAO.saveInvestigationProject(investigationProject);
        if (action == 1){
            showConfirmationAlert();
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }else {
            System.out.println("No se ha guardado nada");
        }
    }

    public void exitButtonEvent(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitSaveProjectAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();

        Stage stagePrincipal = (Stage) saveButton.getScene().getWindow();
        stagePrincipal.close();
    }

    public void showConfirmationAlert() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConfirmationAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
