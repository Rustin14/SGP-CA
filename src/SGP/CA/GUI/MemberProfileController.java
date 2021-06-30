package SGP.CA.GUI;

import SGP.CA.Domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberProfileController implements Initializable {

    @FXML
    Label fullNameLabel;
    @FXML
    Label institutionLabel;
    @FXML
    Label maxStudiesLabel;
    @FXML
    Label profileLabel;
    @FXML
    Label evidencesLabel;
    @FXML
    Label eventsLabel;
    @FXML
    Label projectsLabel;

    Member member = Member.signedMember;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLabelActions();
        setMemberData();
    }

    public void setMemberData() {
        String fullName = member.getName() +  " " + member.getFirstLastName() + " " + member.getSecondLastName();
        fullNameLabel.setText(fullName);
        institutionLabel.setText(member.getMaximumStudyLevelInstitution());
        maxStudiesLabel.setText(member.getMaximumStudyLevel());
    }

    public void setLabelActions() {
        evidencesLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleEvidences());
        projectsLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleProjects());
        eventsLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleEvents());
    }


}
