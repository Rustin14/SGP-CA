package SGP.CA.GUI;

import SGP.CA.BusinessLogic.ProjectUtilities;
import SGP.CA.BusinessLogic.TextValidations;
import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.EventDAO;
import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.Event;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class ScheduleEventController implements Initializable {

    @FXML
    TextField eventTitleTextField;
    @FXML
    ComboBox eventResponsableCombo;
    @FXML
    TextField registrationDateTextField;
    @FXML
    TextField eventPlaceTextField;
    @FXML
    DatePicker eventDatePicker;
    @FXML
    TextField hourTextField;
    @FXML
    Button scheduleButton;

    TextValidations textValidations = new TextValidations();
    ArrayList<Member> allActiveMembers = new ArrayList<>();
    int selectedIndex = 0;
    Event createdEvent = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TextField> textFields = Arrays.asList(eventTitleTextField, eventPlaceTextField);
        ProjectUtilities.setArrayTextLimit(textFields, 248);
        ProjectUtilities.setTextLimit(hourTextField, 5);
        fillMembersCombo();
        ProjectUtilities.setMinimumDate(eventDatePicker);
        setRegistrationDate();
    }

    public void fillMembersCombo() {
        MemberDAO memberDAO = new MemberDAO();
        ArrayList<Member> allMembers = new ArrayList<>();
        try {
            allMembers = memberDAO.getAllMembers();
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
        for (int i = 0; i < allMembers.size(); i++) {
            if (allMembers.get(i).getActive() == 1) {
                allActiveMembers.add(allMembers.get(i));
            }
        }
        List<String> membersNamesList = new ArrayList<String>();
        for (Member member : allActiveMembers) {
            String fullName = member.getName() + " " + member.getFirstLastName() + " " + member.getSecondLastName();
            membersNamesList.add(fullName);
        }
        ObservableList<String> lineNamesObList = FXCollections.observableList(membersNamesList);

        eventResponsableCombo.getItems().clear();
        eventResponsableCombo.getItems().addAll(lineNamesObList);
    }

    public void setRegistrationDate() {
        String patternDate = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(patternDate);
        String registrationDate = dateFormat.format(Calendar.getInstance().getTime());
        registrationDateTextField.setText(registrationDate);
    }

    public boolean validateTextFields() {
        AlertBuilder alertBuilder = new AlertBuilder();
        List<TextField> textFields = Arrays.asList(eventTitleTextField, eventPlaceTextField, hourTextField);
        for (TextField field : textFields) {
            if (field.getText().isEmpty()) {
                alertBuilder.errorAlert("No dejar campos vac??os.");
                return false;
            }
        }
        if (eventDatePicker.getValue() == null) {
            alertBuilder.errorAlert("No dejar campos vac??os.");
            return false;
        }

        if (eventResponsableCombo.getSelectionModel().isEmpty()) {
            alertBuilder.errorAlert("No dejar campos vac??os.");
            return false;
        }

        if (!textValidations.validateHourFormat(hourTextField.getText())) {
            alertBuilder.errorAlert("Verificar formato de hora (HH:MM)");
            return false;
        }
        return true;
    }

    public boolean createEvent() {
        Event event = new Event();
        Member member = new Member();
        if(validateTextFields()) {
            event.setEventName(eventTitleTextField.getText());
            event.setResponsableName(eventResponsableCombo.getSelectionModel().getSelectedItem().toString());
            selectedIndex = eventResponsableCombo.getSelectionModel().getSelectedIndex();
            member = allActiveMembers.get(selectedIndex);
            event.setIdMember(member.getIdMember());
            event.setRegistrationDate(new Date());
            event.setEventPlace(eventPlaceTextField.getText());
            java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(eventDatePicker.getValue());
            event.setEventDate(gettedDatePickerDate);
            event.setEventHour(hourTextField.getText());
        } else {
            return false;
        }
        createdEvent = event;
        return true;
    }

    public void scheduleEvent() {
        EventDAO eventDAO = new EventDAO();
        int successfulSave = 0;
        if (createEvent()) {
            try {
                successfulSave = eventDAO.saveEvent(createdEvent);
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
        } if (successfulSave == 1) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.successAlert("??Registro exitoso!");
            Stage currentStage = (Stage) scheduleButton.getScene().getWindow();
            currentStage.close();
            ConsultEventsController.getInstance().populateTable();
        }

    }


    public void cancelButton() {
        Stage stage = (Stage) scheduleButton.getScene().getWindow();
        stage.close();
    }



}
