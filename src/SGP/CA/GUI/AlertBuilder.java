package SGP.CA.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertBuilder {
    public void exceptionAlert (String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public void successAlert (String successMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Éxito!");
        alert.setHeaderText(null);
        alert.setContentText(successMessage);
        alert.showAndWait();
    }

    public void errorAlert (String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public boolean confirmationAlert (String confirmationMessage) {
        boolean userResponse = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            userResponse = true;
        } else {
            alert.close();
        }
        return userResponse;
    }
}
