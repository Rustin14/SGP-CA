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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
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

    Member member;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillLGACCombo();
        setTextLimit();
        setMaxDate();
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
        if (birthDateTF.getValue() == null) {
            return false;
        }
        return true;
    }

    public void setTextLimit() {
        final int MAX_CHARS = 252;
        final int MAX_CURP_CHARS = 18;
        final int MAX_PHONE_CHARS = 10;
        List<TextField> textFields = Arrays.asList(memberNameTF, firstLastNameTF,
                secondLastNameTF, maximumTF, institutionTF,
                emailTF, passwordTF, confirmPasswordTF);
        for (TextField field : textFields) {
            field.setTextFormatter(new TextFormatter<String>(change ->
                    change.getControlNewText().length() <= MAX_CHARS ? change : null));
        }

        CURPTF.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CURP_CHARS ? change : null));

        phoneNumberTF.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_PHONE_CHARS ? change : null));
    }

    public void setMaxDate() {
        LocalDate maxDate = LocalDate.now();
        birthDateTF.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate));
                    }});
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

    public boolean createMember() {
        member = new Member();
        if (checkEmptyTextFields()) {
            if (validateCURP()) {
                TextValidations textValidations = new TextValidations();
                if (textValidations.confirmPassword(passwordTF.getText(), confirmPasswordTF.getText())) {
                    if (textValidations.validatePhoneNumber(phoneNumberTF.getText())) {
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
                        }
                        member.setIdLGAC(lgac.getIdLGAC());
                        member.setEmail(emailTF.getText());
                        member.setPassword(passwordTF.getText());
                    } else {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        alertBuilder.errorAlert("El número de teléfono introducido no es válido. Introduzca un número de 10 dígitos.");
                        return false;
                    }
                } else {
                    noCoincidenceLabel.setVisible(true);
                    return false;
                }
            } else {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.errorAlert("Introduzca un CURP válido.");
                return false;
            }
        } else {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.errorAlert("No deje campos vacíos.");
            return false;
        }
        return true;
    }

    public void registerMember() {
        if (createMember()) {
            MemberDAO memberDAO = new MemberDAO();
            AlertBuilder alertBuilder = new AlertBuilder();
            int successfulSave = 0;
                try {
                    successfulSave = memberDAO.saveMember(member);
                } catch (SQLIntegrityConstraintViolationException CURPDuplication) {
                    alertBuilder.exceptionAlert("Ya hay un registro del CURP introducido en la base de datos.");
                } catch (SQLException sqlException) {
                    alertBuilder.exceptionAlert("No hay conexión a la base de datos. Intente más tarde.");
                }
            if (successfulSave == 1) {
                alertBuilder.successAlert("¡Registro realizado!");
                Stage stage = (Stage) academicPositionCombo.getScene().getWindow();
                stage.close();
                ConsultMemberController.getInstance().populateTable();
            }
        }
    }


}
