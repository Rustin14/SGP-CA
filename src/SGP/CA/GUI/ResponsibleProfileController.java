package SGP.CA.GUI;

import SGP.CA.Domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ResponsibleProfileController implements Initializable {

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
    @FXML
    Label workPlanLabel;
    @FXML
    Label membersLabel;

    Member member = Member.signedMember;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResponsibleData();
        setLabelActions();
    }

    public void setResponsibleData() {
        String fullName = member.getName() +  " " + member.getFirstLastName() + " " + member.getSecondLastName();
        fullNameLabel.setText(fullName);
        institutionLabel.setText(member.getMaximumStudyLevelInstitution());
        maxStudiesLabel.setText(member.getMaximumStudyLevel());
    }

    public void setLabelActions() {
        membersLabel.setOnMouseClicked(event -> SceneSwitcher.consultMembers());
        evidencesLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleEvidences());
        workPlanLabel.setOnMouseClicked(event -> SceneSwitcher.consultWorkPlan());
        projectsLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleProjects());
        eventsLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleEvents());
    }




}
