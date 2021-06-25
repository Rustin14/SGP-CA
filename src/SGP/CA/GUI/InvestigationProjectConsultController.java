package SGP.CA.GUI;

import SGP.CA.DataAccess.BluePrintDAO;
import SGP.CA.DataAccess.InvestigationProjectDAO;
import SGP.CA.Domain.BluePrint;
import SGP.CA.Domain.InvestigationProject;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class InvestigationProjectConsultController extends Application {

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

    @FXML
    private ComboBox<String> bluePrintsComboBox;

    @FXML
    private TextField bluePrintTitleTextField;

    @FXML
    private TextField stateTextField;

    private ObservableList<String> investigationProjectTitles = FXCollections.observableArrayList();

    private ArrayList<InvestigationProject> investigationProjects = new ArrayList<>();

    InvestigationProjectConsultController investigationProjectConsultController;


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/InvestigationProjectConsultFXML.fxml"));
        primaryStage.setTitle("Consultar proyecto");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void exitButtonEvent() {
        AlertBuilder alertBuilder = new AlertBuilder();
        boolean confirmationMessage = alertBuilder.confirmationAlert("¿Estas seguro que desea salir?");
        if (confirmationMessage) {
            Stage stagePrincipal = (Stage) exitButton.getScene().getWindow();
            stagePrincipal.close();
        }
    }

    public void modifyButtonEvent() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyInvestigationProjectFXML.fxml"));
            stage.setScene(new Scene(root));
        }catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(modifyButton.getScene().getWindow());
        stage.showAndWait();
        investigationProjectTitles.clear();
        investigationProjects.clear();
        initialize();
    }

    public void addBluePrintButtonEvent() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/AddBluePrintFXML.fxml"));
            stage.setScene(new Scene(root));
        }catch(IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addBluePrintButton.getScene().getWindow());
        stage.showAndWait();
        investigationProjectTitles.clear();
        investigationProjects.clear();
        initialize();
    }

    public void addProjectButtonEvent() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/RegisterInvestigationProjectFXML.fxml"));
            stage.setScene(new Scene(root));
        }catch(IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addProjectButton.getScene().getWindow());
        stage.showAndWait();
        investigationProjectTitles.clear();
        investigationProjects.clear();
        initialize();
    }

    public void comboBoxProjectsEvent() {
        String selectedTitle = comboBoxProjects.getSelectionModel().getSelectedItem();
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        InvestigationProject investigationProject = new InvestigationProject();
        try {
            investigationProject = investigationProjectDAO.searchInvestigationProjectByTitle(selectedTitle);
        }catch(SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        try {
            titleTextField.setText(investigationProject.getProjectTitle());
            DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
            String startDate = setDate.format(investigationProject.getStartDate().getTime());
            startDateTextField.setText(startDate);
            String endingDate = setDate.format(investigationProject.getEstimatedEndDate().getTime());
            endDateTextField.setText(endingDate);
            lgacTextField.setText(investigationProject.getAssociatedLgac());
            participantsTextField.setText(investigationProject.getParticipants());
            descriptionTextField.setText(investigationProject.getDescription());
        }catch(NullPointerException exNullPointerException) {
            titleTextField.clear();
            startDateTextField.clear();
            endDateTextField.clear();
            lgacTextField.clear();
            participantsTextField.clear();
            descriptionTextField.clear();
            comboBoxProjects.getSelectionModel().clearSelection();
        }
    }

    public void bluePrintsComboBoxEvent() {
        String titleSelected = bluePrintsComboBox.getSelectionModel().getSelectedItem();
        BluePrintDAO bluePrintDAO = new BluePrintDAO();
        BluePrint bluePrint = new BluePrint();
        try {
            bluePrint = bluePrintDAO.searchBluePrintByTitle(titleSelected);
        }catch(SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        try {
            stateTextField.setText(bluePrint.getState());
            bluePrintTitleTextField.setText(bluePrint.getBluePrintTitle());
        }catch(NullPointerException exNullPointerException) {
            stateTextField.clear();
            bluePrintTitleTextField.clear();
            bluePrintsComboBox.getSelectionModel().clearSelection();
        }
    }

    public void consultButtonEvent() {
        Stage stage2 = new Stage();
        FXMLLoader loader = new FXMLLoader();
        AnchorPane root = new AnchorPane();
        try {
            root = loader.load(getClass().getResource("FXML/ConsultBluePrintFXML.fxml").openStream());
        }catch(IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo corectamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        ConsultBluePrintController consultBluePrintController = loader.getController();
        consultBluePrintController.getBluePrintTitle(investigationProjectConsultController,bluePrintTitleTextField.getText());
        Scene scene = new Scene(root);
        stage2.setScene(scene);
        stage2.alwaysOnTopProperty();
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.showAndWait();
        investigationProjectTitles.clear();
        investigationProjects.clear();
        initialize();
    }

    @FXML
    public void initialize() {
        investigationProjectConsultController = this;
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        try {
            investigationProjects = investigationProjectDAO.getAllInvestigationProjects();
        }catch(SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }

        for(InvestigationProject investigationProject : investigationProjects) {
            investigationProjectTitles.add(investigationProject.getProjectTitle());
        }
        comboBoxProjects.setItems(investigationProjectTitles);
        BluePrintDAO bluePrintDAO = new BluePrintDAO();
        ArrayList<BluePrint> bluePrints = new ArrayList<>();
        try {
            bluePrints = bluePrintDAO.getAllBluePrints();
        }catch(SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        ObservableList<String> bluePrintTitles = FXCollections.observableArrayList();
        for (BluePrint bluePrint: bluePrints) {
            bluePrintTitles.add(bluePrint.getBluePrintTitle());
        }
        bluePrintsComboBox.setItems(bluePrintTitles);
    }

    public void consultEvidences() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultEvidence")) {
            try {
                ScreenController.instance.addScreen("consultEvidence", FXMLLoader.load(getClass().getResource("FXML/ConsultEvidenceFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultEvidence");
    }

    public void consultEvents() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultEvents")) {
            try {
                ScreenController.instance.addScreen("consultEvents", FXMLLoader.load(getClass().getResource("FXML/ConsultEventsFXML.fxml")));
            }catch(IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultEvents");
    }

    public void goToProfile() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("memberProf")) {
            try {
                ScreenController.instance.addScreen("memberProf", FXMLLoader.load(getClass().getResource("FXML/MemberProfileFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("memberProf");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
