package SGP.CA.GUI;

import SGP.CA.BusinessLogic.TextValidations;
import SGP.CA.DataAccess.LGACDAO;
import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.LGAC;
import SGP.CA.Domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class ModifyMemberController implements Initializable {

    @FXML
    TextField nameTextField;
    @FXML
    TextField firstLastNameTextField;
    @FXML
    TextField secondLastNameTextField;
    @FXML
    TextField curpTextField;
    @FXML
    TextField emailTextField;
    @FXML
    TextField maximumGradeTextField;
    @FXML
    TextField institutionTextField;
    @FXML
    TextField  phoneTextField;
    @FXML
    DatePicker datePicker;
    @FXML
    ComboBox lgacCombo;

    Member member = Member.selectedMember;
    LGACDAO lgacdao = new LGACDAO();
    AlertBuilder alertBuilder = new AlertBuilder();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMemberData();
        fillLGACCombo();
    }

    public boolean checkEmptyTextFields() {
        List<TextField> textFields = Arrays.asList(nameTextField, firstLastNameTextField,
                secondLastNameTextField, curpTextField, phoneTextField, maximumGradeTextField, institutionTextField,
                emailTextField);
        for (TextField field : textFields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void setMemberData () {
        nameTextField.setText(member.getName());
        firstLastNameTextField.setText(member.getFirstLastName());
        secondLastNameTextField.setText(member.getSecondLastName());
        curpTextField.setText(member.getCURP());
        emailTextField.setText(member.getEmail());
        maximumGradeTextField.setText(member.getMaximumStudyLevel());
        institutionTextField.setText(member.getMaximumStudyLevelInstitution());
        LGAC lgac = new LGAC();
        try {
            lgac = lgacdao.searchLGACbyID(member.getIdLGAC());
        } catch (SQLException sqlException) {
            alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
        }
        lgacCombo.getEditor().setText(lgac.getLineName());
        phoneTextField.setText(member.getPhoneNumber());
    }

    public Member createMember() {
        Member member = new Member();
        if (checkEmptyTextFields()) {
            if (validateCURP()) {
                    LGAC lgac = new LGAC();
                    member.setName(nameTextField.getText());
                    member.setFirstLastName(firstLastNameTextField.getText());
                    member.setSecondLastName(secondLastNameTextField.getText());
                    member.setCURP(curpTextField.getText());
                    java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(datePicker.getValue());
                    member.setDateOfBirth(gettedDatePickerDate);
                    member.setPhoneNumber(phoneTextField.getText());
                    member.setMaximumStudyLevel(maximumGradeTextField.getText());
                    member.setMaximumStudyLevelInstitution(institutionTextField.getText());
                    try {
                        LGACDAO lgacdao = new LGACDAO();
                        lgac = lgacdao.searchLGACbyLineName((String) lgacCombo.getSelectionModel().getSelectedItem());
                    } catch (SQLException sqlException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
                        sqlException.printStackTrace();
                    }
                    member.setIdLGAC(lgac.getIdLGAC());
                    member.setEmail(emailTextField.getText());
            } else {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.errorAlert("Introduzca un CURP válido.");
                return null;
            }
        } else {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.errorAlert("No deje campos vacíos.");
            return null;
        }
        return member;
    }

    public boolean validateCURP() {
        TextValidations textValidations = new TextValidations();
        if (textValidations.validateCURPFormat(curpTextField.getText())) {
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

        lgacCombo.getItems().clear();
        lgacCombo.getItems().addAll(lineNamesObList);
    }

    public void modifyMember() {
        Member savedMember = createMember();
        MemberDAO memberDAO = new MemberDAO();
        AlertBuilder alertBuilder = new AlertBuilder();
        int idMember = 0;

        try {
            idMember = memberDAO.searchMemberByName(savedMember.getName()).getIdMember();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        if (savedMember != null) {
            try {
                memberDAO.modifyMember(savedMember, idMember);
            } catch (SQLException sqlException) {
                alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
                sqlException.printStackTrace();
            }

            alertBuilder.successAlert("¡Registro realizado!");
            Stage stage = (Stage) lgacCombo.getScene().getWindow();
            stage.close();
            Button closeWindow = ModalMemberDataController.getInstance().getButton();
            Stage mainStage = (Stage) closeWindow.getScene().getWindow();
            mainStage.close();
            ConsultMemberController.getInstance().populateTable();
        }
    }









}
