package SGP.CA.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.IOException;

public class ConfirmationAlertController extends Application{

    @FXML
    private Button okButton;

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConfirmationAlertFXML.fxml"));
        primaryStage.setTitle("Confirmacion");
        primaryStage.setScene(new Scene(root, 500, 200));
        primaryStage.show();
    }

    public void okButtonEvent (){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
