package SGP.CA.GUI;

import javafx.application.Application;
import javafx.application.Platform;
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

import java.io.IOException;

public class InvestigationProjectConsultController extends Application{

    @FXML
    private Button addBluePrintButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

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
    private TextField stateTextField;

    @FXML
    private TextField lgacTextField;

    @FXML
    private TextField participantsTextField;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private Button addProjectButton;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/InvestigationProjectConsultFXML.fxml"));
        primaryStage.setTitle("Consultar proyecto");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void exitButtonEvent(){
        Platform.exit();
        System.exit(0);
        //TO DO
    }

    public void deleteButtonEvent(){

    }

    public void modifyButtonEvent() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyInvestigationProjectFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addProjectButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void addBluePrintButtonEvent() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/AddBluePrintFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addProjectButton.getScene().getWindow());
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

    public void comboBoxProjectsEvent(){

    }

    public static void main(String[] args) {
        launch(args);
    }
}
