package SGP.CA.GUI;

import SGP.CA.BusinessLogic.HashPasswords;
import SGP.CA.BusinessLogic.ProjectUtilities;
import SGP.CA.BusinessLogic.TextValidations;
import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.LGACDAO;
import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.DataAccess.ResponsibleDAO;
import SGP.CA.Domain.LGAC;
import SGP.CA.Domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
    @FXML
    ComboBox memberTypeCombo;

    Member member;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TextField> textFields = Arrays.asList(memberNameTF, firstLastNameTF,
                secondLastNameTF, maximumTF, institutionTF,
                emailTF, passwordTF, confirmPasswordTF);
        ProjectUtilities.setArrayTextLimit(textFields, 248);
        ProjectUtilities.setTextLimit(CURPTF, 18);
        ProjectUtilities.setTextLimit(phoneNumberTF, 10);
        fillLGACCombo();
        ProjectUtilities.setMaxDate(birthDateTF);
        setMemberTypeCombo();
    }

    public void setMemberTypeCombo() {
        ObservableList<String> memberTypes = FXCollections.observableArrayList("Miembro", "Responsable");
        memberTypeCombo.setItems(memberTypes);
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

        if (memberTypeCombo.getSelectionModel().isEmpty()) {
            return false;
        }
        return true;
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
        } catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
        } finally {
            try {
                ConnectDB.closeConnection();
            } catch (SQLException exSqlException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            }
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
                        } catch (SQLException exSqlException) {
                            AlertBuilder alertBuilder = new AlertBuilder();
                            alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
                        } finally {
                            try {
                                ConnectDB.closeConnection();
                            } catch (SQLException exSqlException) {
                                AlertBuilder alertBuilder = new AlertBuilder();
                                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
                            }
                        }
                        member.setIdLGAC(lgac.getIdLGAC());
                        member.setEmail(emailTF.getText());
                        HashPasswords hashPasswords = new HashPasswords();
                        String password = hashPasswords.generateHashedPass(passwordTF.getText());
                        member.setPassword(password);
                        String memberType = (String) memberTypeCombo.getSelectionModel().getSelectedItem();
                        if (memberType.equals("Responsable")) {
                            member.setIsResponsible(1);
                        }
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

    public void registerResponsible () {
        MemberDAO memberDAO = new MemberDAO();
        ResponsibleDAO responsibleDAO = new ResponsibleDAO();
        AlertBuilder alertBuilder = new AlertBuilder();
        Member responsible = new Member();
        try {
            responsible = memberDAO.searchMemberByName(member.getName());
        } catch (SQLException exSqlException) {
            alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Inténtalo más tarde.");
        } finally {
            try {
                ConnectDB.closeConnection();
            } catch (SQLException exSqlException) {
                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            }
        }
        try {
            responsibleDAO.saveResponsible(responsible.getIdMember());
        } catch (SQLException exSqlException) {
            alertBuilder.exceptionAlert("No hay conexión a la base de datos. Intente más tarde.");
        } finally {
            try {
                ConnectDB.closeConnection();
            } catch (SQLException exSqlException) {
                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            }
        }
    }

    public void registerMember() {
        if (createMember()) {
            MemberDAO memberDAO = new MemberDAO();
            AlertBuilder alertBuilder = new AlertBuilder();
            int successfulSave = 0;
                try {
                    successfulSave = memberDAO.saveMember(member);
                    if (member.getIsResponsible() == 1) {
                        registerResponsible();
                    }
                } catch (SQLIntegrityConstraintViolationException dataDuplication) {
                    String exceptionMessage = dataDuplication.getMessage();
                    String [] messageParts = exceptionMessage.split("key ");
                    if (messageParts[1].equals("'UQ_Name'")) {
                        alertBuilder.exceptionAlert("Ya hay un registro del CURP introducido en la base de datos.");
                    } else {
                        alertBuilder.exceptionAlert("Ya hay un registro del email introducio en la base de datos.");
                    }
                } catch (SQLException exSqlException) {
                    alertBuilder.exceptionAlert("No hay conexión a la base de datos. Intente más tarde.");
                } finally {
                    try {
                        ConnectDB.closeConnection();
                    } catch (SQLException exSqlException) {
                        alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
                    }
                }
            if (successfulSave == 1) {
                alertBuilder.successAlert("¡Registro realizado!");
                Stage stage = (Stage) academicPositionCombo.getScene().getWindow();
                stage.close();
                ConsultMemberController.getInstance().populateTable();
            }
        }
    }

    public void cancelButton() {
        Stage stage = (Stage) memberTypeCombo.getScene().getWindow();
        stage.close();
    }


}
