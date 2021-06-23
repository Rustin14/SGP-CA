package SGP.CA.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ExitConsultBluePrintAlertController extends Application{

    @FXML
    private Button cancelButton;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitConsultBluePrintAlertFXML.fxml"));
        primaryStage.setTitle("Salir");
        primaryStage.setScene(new Scene(root, 500, 200));
        primaryStage.show();
    }

    public void cancelButtonEvent (){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void acceptButtonEvent (){
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
