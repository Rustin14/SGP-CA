package SGP.CA.GUI;

import SGP.CA.BusinessLogic.TextValidations;
import SGP.CA.DataAccess.LGACDAO;
import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.LGAC;
import SGP.CA.Domain.Member;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class RegisterMemberController implements Initializable {

    @FXML
    TextField memberNameTF;
    @FXML
    TextField firstLastNameTF;
    @FXML
    TextField secondLastNameTF;
    @FXML
    TextField CURPTF;
    @FXML
    DatePicker birthDateTF;
    @FXML
    TextField phoneNumberTF;
    @FXML
    TextField maximumTF;
    @FXML
    TextField institutionTF;
    @FXML
    ComboBox LGACCombo;
    @FXML
    TextField emailTF;
    @FXML
    TextField passwordTF;
    @FXML
    TextField confirmPasswordTF;
    @FXML
    Label noCoincidenceLabel;
    @FXML
    Label CURPLabel;
    @FXML
    ComboBox academicPositionCombo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillLGACCombo();
    }

    public boolean checkEmptyTextFields() {
        List<TextField> textFields = Arrays.asList(memberNameTF, firstLastNameTF,
                secondLastNameTF, CURPTF, phoneNumberTF, maximumTF, institutionTF,
                emailTF, passwordTF, confirmPasswordTF);
        for (TextField field : textFields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void changeToColaborator() {
        academicPositionCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {

            }
        });
    }
    public boolean validateCURP() {
        TextValidations textValidations = new TextValidations();
        if (textValidations.validateCURPFormat(CURPTF.getText())) {
            return true;
        } else {
            return false;
        }
    }

    public void fillLGACCombo() {
        LGACDAO lgacdao = new LGACDAO();
        ArrayList<LGAC> allLines = new ArrayList<>();
        try {
            allLines = lgacdao.getAllLines();
        } catch (SQLException sqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
            sqlException.printStackTrace();
        }

        List<String> lineNamesList = new ArrayList<String>();
        for (LGAC allLine : allLines) {
            lineNamesList.add(allLine.getLineName());
        }
        ObservableList<String> lineNamesObList = FXCollections.observableList(lineNamesList);

        LGACCombo.getItems().clear();
        LGACCombo.getItems().addAll(lineNamesObList);
    }

    public Member createMember() {
        Member member = new Member();
        if (checkEmptyTextFields()) {
            if (validateCURP()) {
                TextValidations textValidations = new TextValidations();
                if (textValidations.confirmPassword(passwordTF.getText(), confirmPasswordTF.getText())) {
                    LGAC lgac = new LGAC();

                    member.setName(memberNameTF.getText());
                    member.setFirstLastName(firstLastNameTF.getText());
                    member.setSecondLastName(secondLastNameTF.getText());
                    member.setCURP(CURPTF.getText());
                    java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(birthDateTF.getValue());
                    member.setDateOfBirth(gettedDatePickerDate);
                    member.setPhoneNumber(phoneNumberTF.getText());
                    member.setMaximumStudyLevel(maximumTF.getText());
                    member.setMaximumStudyLevelInstitution(institutionTF.getText());
                    try {
                        LGACDAO lgacdao = new LGACDAO();
                        lgac = lgacdao.searchLGACbyLineName((String) LGACCombo.getSelectionModel().getSelectedItem());
                    } catch (SQLException sqlException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
                        sqlException.printStackTrace();
                    }
                    member.setIdLGAC(lgac.getIdLGAC());
                    member.setEmail(emailTF.getText());
                    member.setPassword(passwordTF.getText());
                } else {
                    noCoincidenceLabel.setVisible(true);
                    return null;
                }
            } else {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.errorAlert("Introduzca un CURP válido.");
                CURPTF.setStyle("-fx-color: red");
                CURPLabel.setStyle("-fx-color: red");
                return null;
            }
        } else {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.errorAlert("No deje campos vacíos.");
            return null;
        }
        return member;
    }

    public void registerMember() {
        Member savedMember = createMember();
        MemberDAO memberDAO = new MemberDAO();
        AlertBuilder alertBuilder = new AlertBuilder();

        if (savedMember != null) {
            try {
                memberDAO.saveMember(savedMember);
            } catch (SQLException sqlException) {
                alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
                sqlException.printStackTrace();
            }

            alertBuilder.successAlert("¡Registro realizado!");
        }
    }


}
