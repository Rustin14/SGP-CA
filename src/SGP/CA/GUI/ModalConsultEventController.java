package SGP.CA.GUI;

import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.EventDAO;
import SGP.CA.Domain.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ModalConsultEventController implements Initializable {

    @FXML
    Label titleEventLabel;
    @FXML
    Label registrationDateLabel;
    @FXML
    Label eventDateLabel;
    @FXML
    Label eventPlaceLabel;
    @FXML
    Label eventHourLabel;
    @FXML
    Label responsableLabel;
    @FXML
    Button deleteButton;

    Event event = Event.selectedEvent;
    EventDAO eventDAO = new EventDAO();

    private static ModalConsultEventController instance;

    public static ModalConsultEventController getInstance() {
        return instance;
    }

    public ModalConsultEventController(){
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEventData();
    }

    public void setEventData() {
        titleEventLabel.setText(event.getEventName());
        String patternDate = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(patternDate);
        String registrationDate = dateFormat.format(event.getRegistrationDate());
        registrationDateLabel.setText(registrationDate);
        String eventDate = dateFormat.format(event.getEventDate());
        eventDateLabel.setText(eventDate);
        eventPlaceLabel.setText(event.getEventPlace());
        responsableLabel.setText(event.getResponsableName());
        eventHourLabel.setText(event.getEventHour());
    }

    public void deleteEvent() {
        AlertBuilder alertBuilder = new AlertBuilder();
        boolean userResponse = alertBuilder.confirmationAlert("¿Está seguro de eliminar este evento?");
        int successfulUpdate = 0;
        if (userResponse) {
            try {
                successfulUpdate = eventDAO.deleteEvent(event.getIdEvent());
            } catch (SQLException sqlException) {
                alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
            } finally {
                try {
                    ConnectDB.closeConnection();
                } catch (SQLException exSqlException) {
                    alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
                }
            }
        }
        if (successfulUpdate > 0) {
            alertBuilder.successAlert("Evento eliminado.");
            Stage currentStage = (Stage) deleteButton.getScene().getWindow();
            currentStage.close();
            if (ConsultEventsController.getInstance() == null) {
                ConsultEventsResponsibleController.getInstance().populateTable();
            } else {
                ConsultEventsController.getInstance().populateTable();
            }
        }
    }

    public void modifyEvent() {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        try {
            sceneSwitcher.createDialog((Stage) deleteButton.getScene().getWindow(), "FXML/ModifyEventFXML.fxml");
        } catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        }
    }

    public void cancelButton() {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }


}
