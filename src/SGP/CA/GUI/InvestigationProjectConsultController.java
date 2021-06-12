package SGP.CA.GUI;

import javafx.application.Application;
import javafx.application.Platform;
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
import SGP.CA.Domain.InvestigationProject;
import SGP.CA.DataAccess.InvestigationProjectDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class InvestigationProjectConsultController extends Application{

    @FXML
    private Button addBluePrintButton;

    @FXML
    private Button modifyButton;

    @FXML
    private ComboBox<String> comboBoxProjects;

    @FXML
    private Button exitButton;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField startDateTextField;

    @FXML
    private TextField endDateTextField;

    @FXML
    private TextField lgacTextField;

    @FXML
    private TextField participantsTextField;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private Button addProjectButton;

    private ObservableList<String> investigationProjectTitles = FXCollections.observableArrayList();

    private ArrayList<InvestigationProject> investigationProjects = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/InvestigationProjectConsultFXML.fxml"));
        primaryStage.setTitle("Consultar proyecto");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void exitButtonEvent() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitConsultProjectAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(exitButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void modifyButtonEvent() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyInvestigationProjectFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(modifyButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void addBluePrintButtonEvent() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/AddBluePrintFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addBluePrintButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void addProjectButtonEvent() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/RegisterInvestigationProjectFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addProjectButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void comboBoxProjectsEvent() throws SQLException, ClassNotFoundException{
        String selectedTitle = comboBoxProjects.getSelectionModel().getSelectedItem();
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        InvestigationProject investigationProject = investigationProjectDAO.searchInvestigationProjectByTitle(selectedTitle);
        titleTextField.setText(investigationProject.getProjectTitle());
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String startDate = setDate.format(investigationProject.getStartDate().getTime());
        startDateTextField.setText(startDate);
        String endingDate = setDate.format(investigationProject.getEstimatedEndDate().getTime());
        endDateTextField.setText(endingDate);
        lgacTextField.setText(investigationProject.getAssociatedLgac());
        participantsTextField.setText(investigationProject.getParticipants());
        descriptionTextField.setText(investigationProject.getDescription());
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        investigationProjects = investigationProjectDAO.getAllInvestigationProjects();
        for(InvestigationProject investigationProject : investigationProjects){
            investigationProjectTitles.add(investigationProject.getProjectTitle());
        }
        comboBoxProjects.setItems(investigationProjectTitles);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
