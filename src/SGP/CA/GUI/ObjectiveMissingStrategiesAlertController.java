package SGP.CA.GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class ObjectiveMissingStrategiesAlertController extends Application {

    @FXML
    private Button okButton;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ObjectiveMissingStrategiesAlertFXML.fxml"));
        primaryStage.setTitle("Falta estrategias");
        primaryStage.setScene(new Scene(root, 500, 200));
        primaryStage.show();
    }

    public void okButtonEvent() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
