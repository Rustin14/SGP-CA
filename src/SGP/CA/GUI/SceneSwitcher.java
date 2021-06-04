package SGP.CA.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public void createDialog(Stage parentStage, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        dialog.setScene(scene);
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
}
