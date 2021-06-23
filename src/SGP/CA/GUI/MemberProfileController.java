package SGP.CA.GUI;

import SGP.CA.Domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MemberProfileController implements Initializable {

    @FXML
    Label fullNameLabel;
    @FXML
    Label institutionLabel;
    @FXML
    Label maxStudiesLabel;

    AlertBuilder alertBuilder = new AlertBuilder();
    Member member = Member.signedMember;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMemberData();
    }

    public void setMemberData() {
        String fullName = member.getName() +  " " + member.getFirstLastName() + " " + member.getSecondLastName();
        fullNameLabel.setText(fullName);
        institutionLabel.setText(member.getMaximumStudyLevelInstitution());
        maxStudiesLabel.setText(member.getMaximumStudyLevel());
    }

    public void consultEvidence() {
        if(!ScreenController.instance.isScreenOnMap("consultEvidence")) {
            try {
                ScreenController.instance.addScreen("consultEvidence", FXMLLoader.load(getClass().getResource("FXML/ConsultEvidenceFXML.fxml")));
            } catch (IOException ioException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultEvidence");
    }

    public void consultEvents() {
        if(!ScreenController.instance.isScreenOnMap("consultEvent")) {
            try {
                ScreenController.instance.addScreen("consultEvent", FXMLLoader.load(getClass().getResource("FXML/ConsultEventsFXML.fxml")));
            } catch (IOException ioException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultEvent");
    }


}
