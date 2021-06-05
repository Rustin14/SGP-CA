package SGP.CA.GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import SGP.CA.DataAccess.BluePrintDAO;
import SGP.CA.Domain.BluePrint;

public class ModifyBluePrintController extends Application {

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField receptionWorkNameTextField;

    @FXML
    private TextField bluePrintTitleTextField;

    @FXML
    private TextField starDateTextField;

    @FXML
    private TextField associatedLgacTextField;

    @FXML
    private TextField stateTextField;

    @FXML
    private TextField directorTextField;

    @FXML
    private TextField coDirectorTextField;

    @FXML
    private TextField durationTextField;

    @FXML
    private TextField modalityTextField;

    @FXML
    private TextField requirementsTextField;

    @FXML
    private TextField studentTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox<String> bluePrintsComboBox;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyBluePrintFXML.fxml"));
        primaryStage.setTitle("Modificar anteproyecto ");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void bluePrintSelectedEvent(ActionEvent event) {

    }

    public void cancelButtonEvent(ActionEvent event) {

    }

    public void saveButtonEvent(ActionEvent event) {

    }

    @FXML
    public void initialize(){

    }
}
