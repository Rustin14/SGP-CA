package SGP.CA.GUI;

import SGP.CA.DataAccess.LGACDAO;
import SGP.CA.Domain.LGAC;
import SGP.CA.Domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.SQLException;

public class Data {

    @FXML
    Label memberNameLabel;
    @FXML
    Label maximumStudiesLabel;
    @FXML
    Label institutionLabel;
    @FXML
    Label lgacLabel;
    @FXML
    Label emailLabel;
    @FXML
    Label phoneNumberLabel;
    @FXML
    Label curpLabel;
    @FXML
    AnchorPane memberAnchor;


    public Data() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("SGP/CA/GUI/FXML/MemberCellItem.fxml"));
        fxmlLoader.setController(this);
        try {
            memberAnchor = fxmlLoader.load();
        }
        catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public void setInfo(Member member) {
        memberNameLabel.setText(member.getName());
        maximumStudiesLabel.setText(member.getMaximumStudyLevel());
        institutionLabel.setText(member.getMaximumStudyLevelInstitution());
        LGACDAO lgacdao = new LGACDAO();
        LGAC lgac = new LGAC();
        try {
            lgac = lgacdao.searchLGACbyID(member.getIdLGAC());
        } catch (SQLException sqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
            sqlException.printStackTrace();
        }
        lgacLabel.setText(lgac.getLineName());
        emailLabel.setText(member.getEmail());
        phoneNumberLabel.setText(member.getPhoneNumber());
        curpLabel.setText(member.getCURP());
    }

    public AnchorPane getBox() {
        return memberAnchor;
    }
}
