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

    public static void goToResponsibleProfile() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("responsibleProf")) {
            try {
                ScreenController.instance.addScreen("responsibleProf", FXMLLoader.load(SceneSwitcher.class.getResource("FXML/ResponsibleProfileFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("responsibleProf");
    }

    public static void goToMemberProfile() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("memberProf")) {
            try {
                ScreenController.instance.addScreen("memberProf", FXMLLoader.load(SceneSwitcher.class.getResource("FXML/MemberProfileFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("memberProf");
    }


    public static void consultResponsibleEvidences() {
        if(!ScreenController.instance.isScreenOnMap("consultResponsibleEvidences")) {
            try {
                ScreenController.instance.addScreen("consultResponsibleEvidences", FXMLLoader.load(SceneSwitcher.class.getResource("FXML/ConsultEvidenceResponsibleFXML.fxml")));
            } catch (IOException exIoException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultResponsibleEvidences");
    }

    public static void consultResponsibleEvents() {
        if(!ScreenController.instance.isScreenOnMap("consultResponsibleEvents")) {
            try {
                ScreenController.instance.addScreen("consultResponsibleEvents", FXMLLoader.load(SceneSwitcher.class.getResource("FXML/ConsultEventsResponsibleFXML.fxml")));
            } catch (IOException exIoException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultResponsibleEvents");
    }

    public static void consultWorkPlan() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultWorkPlan")) {
            try {
                ScreenController.instance.addScreen("consultWorkPlan", FXMLLoader.load(SceneSwitcher.class.getResource("FXML/ConsultWorkPlanFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultWorkPlan");
    }

    public static void consultResponsibleProjects() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultResponsibleProjects")) {
            try {
                ScreenController.instance.addScreen("consultResponsibleProjects", FXMLLoader.load(SceneSwitcher.class.getResource("FXML/InvestigationProjectConsultResponsibleFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultResponsibleProjects");
    }

    public static void consultMembers() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultMember")) {
            try {
                ScreenController.instance.addScreen("consultMember", FXMLLoader.load(SceneSwitcher.class.getResource("FXML/ConsultMemberFXML.fxml")));
            } catch (IOException ioException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultMember");
    }

    public static void consultProjects() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultProjects")) {
            try {
                ScreenController.instance.addScreen("consultProjects", FXMLLoader.load(SceneSwitcher.class.getResource("FXML/InvestigationProjectConsultFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultProjects");
    }

    public static void consultEvidence() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultEvidence")) {
            try {
                ScreenController.instance.addScreen("consultEvidence", FXMLLoader.load(SceneSwitcher.class.getResource("FXML/ConsultEvidenceFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultEvidence");
    }

    public static void consultEvents() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultEvent")) {
            try {
                ScreenController.instance.addScreen("consultEvent", FXMLLoader.load(SceneSwitcher.class.getResource("FXML/ConsultEventsFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultEvent");
    }

}
