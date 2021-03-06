package SGP.CA.GUI;

import SGP.CA.BusinessLogic.ProjectUtilities;
import SGP.CA.BusinessLogic.TextValidations;
import SGP.CA.DataAccess.ConnectDB;
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
    Member modifiedMember;
    LGACDAO lgacdao = new LGACDAO();
    AlertBuilder alertBuilder = new AlertBuilder();
    TextValidations textValidations = new TextValidations();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TextField> textFields = Arrays.asList(nameTextField, firstLastNameTextField,
                secondLastNameTextField, maximumGradeTextField, institutionTextField,
                emailTextField);
        ProjectUtilities.setArrayTextLimit(textFields, 248);
        ProjectUtilities.setTextLimit(phoneTextField, 10);
        setMemberData();
        fillLGACCombo();
        ProjectUtilities.setMaxDate(datePicker);
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

        if(datePicker.getValue() == null) {
            return false;
        }

        if (lgacCombo.getSelectionModel().getSelectedItem() == null) {
            return false;
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
            alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente m??s tarde.");
        }
        lgacCombo.getEditor().setText(lgac.getLineName());
        phoneTextField.setText(member.getPhoneNumber());
    }

    public boolean createMember() {
        modifiedMember = new Member();
        if (checkEmptyTextFields()) {
            if (textValidations.validatePhoneNumber(phoneTextField.getText())) {
                LGAC lgac = new LGAC();
                modifiedMember.setName(nameTextField.getText());
                modifiedMember.setFirstLastName(firstLastNameTextField.getText());
                modifiedMember.setSecondLastName(secondLastNameTextField.getText());
                modifiedMember.setCURP(curpTextField.getText());
                java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(datePicker.getValue());
                modifiedMember.setDateOfBirth(gettedDatePickerDate);
                modifiedMember.setPhoneNumber(phoneTextField.getText());
                modifiedMember.setMaximumStudyLevel(maximumGradeTextField.getText());
                modifiedMember.setMaximumStudyLevelInstitution(institutionTextField.getText());
                try {
                    LGACDAO lgacdao = new LGACDAO();
                    lgac = lgacdao.searchLGACbyLineName((String) lgacCombo.getSelectionModel().getSelectedItem());
                } catch (SQLException exSqlException) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente m??s tarde.");
                } finally {
                    try {
                        ConnectDB.closeConnection();
                    } catch (SQLException exSqlException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente m??s tarde.");
                    }
                }
                modifiedMember.setIdLGAC(lgac.getIdLGAC());
                modifiedMember.setEmail(emailTextField.getText());
            } else {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.errorAlert("El n??mero de tel??fono introducido no es v??lido. Introduzca un n??mero de 10 d??gitos.");
                return false;
            }
        } else {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.errorAlert("No deje campos vac??os.");
            return false;
        }
        return true;
    }

    public void fillLGACCombo() {
        LGACDAO lgacdao = new LGACDAO();
        ArrayList<LGAC> allLines = new ArrayList<>();
        try {
            allLines = lgacdao.getAllLines();
        } catch (SQLException sqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente m??s tarde.");
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
        if (createMember()) {
            MemberDAO memberDAO = new MemberDAO();
            AlertBuilder alertBuilder = new AlertBuilder();
            int successfulUpdate = 0;
            try {
                successfulUpdate = memberDAO.modifyMember(modifiedMember, member.getIdMember());
            } catch (SQLException exSqlException) {
                alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente m??s tarde.");
            } finally {
                try {
                    ConnectDB.closeConnection();
                } catch (SQLException exSqlException) {
                    alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente m??s tarde.");
                }
            }
            if (successfulUpdate == 1) {
                alertBuilder.successAlert("??Registro realizado!");
                Stage stage = (Stage) lgacCombo.getScene().getWindow();
                stage.close();
                Button closeWindow = ModalMemberDataController.getInstance().getButton();
                Stage mainStage = (Stage) closeWindow.getScene().getWindow();
                mainStage.close();
                ConsultMemberController.getInstance().populateTable();
            }
        }
    }

    public void cancelButton() {
        Stage stage = (Stage) lgacCombo.getScene().getWindow();
        stage.close();
    }

}
