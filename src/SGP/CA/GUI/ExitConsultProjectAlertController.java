package SGP.CA.GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class ExitConsultProjectAlertController extends Application{

    @FXML
    private Button cancelButton;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitConsultProjectAlertFXML.fxml"));
        primaryStage.setTitle("Confirmacion");
        primaryStage.setScene(new Scene(root, 500, 200));
        primaryStage.show();
    }

    public void acceptButtonEvent(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void cancelButtonEvent(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
