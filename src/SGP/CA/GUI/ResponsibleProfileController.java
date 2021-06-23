package SGP.CA.GUI;

import SGP.CA.Domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResponsibleProfileController implements Initializable {

    @FXML
    Label fullNameLabel;
    @FXML
    Label institutionLabel;
    @FXML
    Label maxStudiesLabel;

    Member member = Member.signedMember;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResponsibleData();
    }

    public void setResponsibleData() {
        String fullName = member.getName() +  " " + member.getFirstLastName() + " " + member.getSecondLastName();
        fullNameLabel.setText(fullName);
        institutionLabel.setText(member.getMaximumStudyLevelInstitution());
        maxStudiesLabel.setText(member.getMaximumStudyLevel());
    }

    public void consultMembers() {
        if(!ScreenController.instance.isScreenOnMap("consultMember")) {
            try {
                ScreenController.instance.addScreen("consultMember", FXMLLoader.load(getClass().getResource("FXML/ConsultMemberFXML.fxml")));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        ScreenController.instance.activate("consultMember");
    }




}
