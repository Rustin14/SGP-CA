package SGP.CA.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ExitAddObjectiveAlertController extends Application{

    @FXML
    private Button cancelButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/FailedRegisterAlertFXML.fxml"));
        primaryStage.setTitle("añadir objetivo ");
        primaryStage.setScene(new Scene(root, 500, 200));
        primaryStage.show();
    }

    public void acceptButtonEvent(ActionEvent event){
        Platform.exit();
        System.exit(0);
    }

    public void cancelButtonEvent(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
