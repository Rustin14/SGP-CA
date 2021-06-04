package SGP.CA.GUI;

import SGP.CA.DataAccess.LGACDAO;
import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModalMemberDataController implements Initializable {

    @FXML
    private Label completeNameLabel;
    @FXML
    private Label curpLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label maximumStudiesLabel;
    @FXML
    private Label maximumInstitutionLabel;
    @FXML
    private Label lgacLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label birthDateLabel;
    @FXML
    private Button deleteButton;

    Member member = Member.selectedMember;
    AlertBuilder alertBuilder = new AlertBuilder();

    private static ModalMemberDataController memberDataController;

    public ModalMemberDataController () {
        memberDataController = this;
    }

    public static ModalMemberDataController getInstance() {
        return memberDataController;
    }

    public Button getButton () {
        return deleteButton;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMemberData();
    }

    public void setMemberData () {
        String fullName = member.getName() + " " +  member.getFirstLastName() +  " " + member.getSecondLastName();
        completeNameLabel.setText(fullName);
        curpLabel.setText(member.getCURP());
        emailLabel.setText(member.getEmail());
        maximumInstitutionLabel.setText(member.getMaximumStudyLevelInstitution());
        maximumStudiesLabel.setText(member.getMaximumStudyLevel());
        LGACDAO lgacdao = new LGACDAO();
        String lgacName = "";
        try {
            lgacName = lgacdao.searchLGACbyID(member.getIdLGAC()).getLineName();
        } catch (SQLException sqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        }
        lgacLabel.setText(lgacName);
        phoneLabel.setText(member.getPhoneNumber());
        birthDateLabel.setText(member.getDateOfBirth().toString());
    }

    public void deleteMember() {
        MemberDAO memberDAO = new MemberDAO();
        boolean userResponse = alertBuilder.confirmationAlert("¿Está seguro de eliminar al Miembro?");
        int databaseResponse = -1;
        if (userResponse) {
            try {
                databaseResponse = memberDAO.deleteMember(member.getIdMember());
            } catch (SQLException sqlException) {
                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            }
        }
        if (databaseResponse == 1) {
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            stage.close();
            ConsultMemberController.getInstance().populateTable();
        }
    }

    public void modifyMember() {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        try {
            sceneSwitcher.createDialog((Stage) completeNameLabel.getScene().getWindow(), "FXML/ModifyMemberFXML.fxml");
        } catch (IOException ioException) {
            //alertBuilder.exceptionAlert("Error accediendo a la ventana. Intente de nuevo.");
            ioException.printStackTrace();
        }
    }

}
