package SGP.CA.GUI;

import SGP.CA.BusinessLogic.ProjectUtilities;
import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.EventDAO;
import SGP.CA.Domain.Event;
import SGP.CA.Domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultEventsController implements Initializable {

    @FXML
    TableView eventsTable;
    @FXML
    TableColumn eventNameColumn;
    @FXML
    TableColumn dateColumn;
    @FXML
    TextField searchBar;
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

    private static ConsultEventsController instance;

    public static ConsultEventsController getInstance() {
        return instance;
    }

    public ConsultEventsController () {
        instance = this;
    }

    EventDAO eventDAO = new EventDAO();
    ArrayList<Event> allEvents = new ArrayList<>();
    ObservableList<Event> eventList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
        ProjectUtilities.openDataModal(eventsTable, "FXML/ModalConsultEventFXML.fxml");
        ProjectUtilities.searchBar(searchBar, eventsTable, eventList);
        ProjectUtilities.setTextLimit(searchBar, 50);
        setLabelActions();
    }

    public void populateTable() {
        ArrayList<Event> allActiveEvents = new ArrayList<>();
        ObservableList<Event> auxiliarEventList = FXCollections.observableArrayList();

        try {
            allEvents = eventDAO.getAllEvent();
        } catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        } finally {
            try {
                ConnectDB.closeConnection();
            } catch (SQLException exSqlException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            }
        }
        for (int i = 0; i < allEvents.size(); i++) {
            if (allEvents.get(i).getActive() == 1) {
                allActiveEvents.add(allEvents.get(i));
                auxiliarEventList.add(allEvents.get(i));
            }
        }
        eventList = auxiliarEventList;
        ProjectUtilities.searchBar(searchBar, eventsTable, eventList);
    }

    public void setListView() {
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDate"));
        populateTable();
    }

    public void scheduleEvent() {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        try {
            sceneSwitcher.createDialog((Stage) eventsTable.getScene().getWindow(), "FXML/ScheduleEventFXML.fxml");
        } catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
        }
    }

    public void setLabelActions() {
        if (Member.signedMember.getIsResponsible() == 1) {
            profileLabel.setOnMouseClicked(event -> SceneSwitcher.goToResponsibleProfile());
            membersLabel.setOnMouseClicked(event -> SceneSwitcher.consultMembers());
            evidencesLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleEvidences());
            workPlanLabel.setOnMouseClicked(event -> SceneSwitcher.consultWorkPlan());
            projectsLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleProjects());
        } else {
            profileLabel.setOnMouseClicked(event -> SceneSwitcher.goToMemberProfile());
            evidencesLabel.setOnMouseClicked(event -> SceneSwitcher.consultEvidence());
            projectsLabel.setOnMouseClicked(event -> SceneSwitcher.consultProjects());
        }
    }



}