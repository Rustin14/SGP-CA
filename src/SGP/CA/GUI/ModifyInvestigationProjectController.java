package SGP.CA.GUI;

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
import javafx.application.Application;
import SGP.CA.Domain.InvestigationProject;
import SGP.CA.DataAccess.InvestigationProjectDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyInvestigationProjectController extends Application{

    @FXML
    private Button exitButton;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField projectTitleField;

    @FXML
    private TextField endDateField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField lgacField;

    @FXML
    private TextField participantsField;

    @FXML
    private TextField projectManagerField;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> projectsTitleComboBox;

    public void start(Stage primaryStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyInvestigationProjectFXML.fxml"));
        primaryStage.setTitle("Modificar proyecto");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void saveButtonEvent () throws ParseException, SQLException, IOException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        String oldTitle = projectsTitleComboBox.getSelectionModel().getSelectedItem();
        InvestigationProject investigationProject = new InvestigationProject();
        investigationProject.setProjectTitle(projectTitleField.getText());
        String endDate = endDateField.getText();
        Date estimateEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
        investigationProject.setEstimatedEndDate(estimateEndDate);
        String startDateString = startDateField.getText();
        Date starDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
        investigationProject.setStartDate(starDate);
        investigationProject.setAssociatedLgac(lgacField.getText());
        investigationProject.setParticipants(participantsField.getText());
        investigationProject.setProjectManager(projectManagerField.getText());
        investigationProject.setDescription(descriptionField.getText());
        int result = investigationProjectDAO.modifyInvestigationProject(investigationProject, oldTitle);
        if (result == 1){
            showConfirmationAlert();
            Stage stagePrincipal = (Stage) saveButton.getScene().getWindow();
            stagePrincipal.close();
        }else {
            System.out.println("Error");
            //Todo
        }
    }

    public void exitButtonEvent() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitSaveProjectAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(exitButton.getScene().getWindow());
        stage.showAndWait();

        Stage stagePrincipal = (Stage) exitButton.getScene().getWindow();
        stagePrincipal.close();
    }

    public void projectsTitleComboBoxEvent() throws SQLException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        String title = projectsTitleComboBox.getSelectionModel().getSelectedItem();
        InvestigationProject investigationProject = investigationProjectDAO.searchInvestigationProjectByTitle(title);
        projectTitleField.setText(investigationProject.getProjectTitle());
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String endingDate = setDate.format(investigationProject.getEstimatedEndDate().getTime());
        endDateField.setText(endingDate);
        String startDate = setDate.format(investigationProject.getStartDate().getTime());
        startDateField.setText(startDate);
        lgacField.setText(investigationProject.getAssociatedLgac());
        participantsField.setText(investigationProject.getParticipants());
        projectManagerField.setText(investigationProject.getProjectManager());
        descriptionField.setText(investigationProject.getDescription());
    }

    @FXML
    public void initialize() throws SQLException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        ArrayList<InvestigationProject> allProjects = investigationProjectDAO.getAllInvestigationProjects();
        ObservableList<String> allProjectsTile = FXCollections.observableArrayList();
        for (int i=0; i< allProjects.size(); i++){
            allProjectsTile.add(allProjects.get(i).getProjectTitle());
        }
        projectsTitleComboBox.setItems(allProjectsTile);
    }

    public void showConfirmationAlert() throws IOException {
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
