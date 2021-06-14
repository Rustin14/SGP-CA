package SGP.CA.GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConsultObjectiveController extends Application{

    @FXML
    private TextField objectiveTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextArea strategyTextArea;

    @FXML
    private TextArea goalTextArea;

    @FXML
    private TextArea actionTextArea;

    @FXML
    private TextArea resultTextArea;

    @FXML
    private Button closeButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConsultObjectiveFXML.fxml"));
        primaryStage.setTitle("Objetivo");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
