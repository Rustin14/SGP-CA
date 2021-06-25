package SGP.CA.GUI;

import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.EventDAO;
import SGP.CA.Domain.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultEventsResponsibleController implements Initializable {

    @FXML
    TableView eventsTable;
    @FXML
    TableColumn eventNameColumn;
    @FXML
    TableColumn dateColumn;
    @FXML
    TextField searchBar;

    private static ConsultEventsResponsibleController instance;

    public static ConsultEventsResponsibleController getInstance() {
        return instance;
    }

    public ConsultEventsResponsibleController() {
        instance = this;
    }

    EventDAO eventDAO = new EventDAO();
    ArrayList<Event> allEvents = new ArrayList<>();
    ObservableList<Event> eventList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
        openEventDataModal();
        searchEvent();
        setTextLimit();
    }

    public void setTextLimit () {
        final int MAX_CHARS = 50;
        searchBar.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));
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
        searchEvent();
    }

    public void setListView() {
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDate"));
        populateTable();
    }

    public void openEventDataModal() {
        eventsTable.setRowFactory( tv -> {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Event.selectedEvent = row.getItem();
                    SceneSwitcher sceneSwitcher = new SceneSwitcher();
                    try {
                        sceneSwitcher.createDialog((Stage) eventsTable.getScene().getWindow(), "FXML/ModalConsultEventFXML.fxml");
                    } catch (IOException exIoException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        alertBuilder.exceptionAlert("Error cargando la ventana. Intente de nuevo.");
                    }
                }
            });
            return row;
        });
    }

    public void scheduleEvent() {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        try {
            sceneSwitcher.createDialog((Stage) eventsTable.getScene().getWindow(), "FXML/ScheduleEventFXML.fxml");
        } catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        }
    }

    public void searchEvent() {
        FilteredList<Event> filteredData = new FilteredList<>(eventList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
            String lowerCaseFilter = newValue.toLowerCase();
                if (event.getEventName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Event> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(eventsTable.comparatorProperty());
        eventsTable.setItems(sortedData);
    }

    public void consultEvidence() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultEvidence")) {
            try {
                ScreenController.instance.addScreen("consultEvidence", FXMLLoader.load(getClass().getResource("FXML/ConsultEvidenceFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultEvidence");
    }

    public void goToProfile() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("responsibleProf")) {
            try {
                ScreenController.instance.addScreen("responsibleProf", FXMLLoader.load(getClass().getResource("FXML/ResponsibleProfileFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("responsibleProf");
    }

    public void consultWorkPlan() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultWorkPlan")) {
            try {
                ScreenController.instance.addScreen("consultWorkPlan", FXMLLoader.load(getClass().getResource("FXML/ConsultWorkPlanFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultWorkPlan");
    }

    public void consultProjects() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultProjects")) {
            try {
                ScreenController.instance.addScreen("consultProjects", FXMLLoader.load(getClass().getResource("FXML/InvestigationProjectConsultResponsibleFXML.fxml")));
            } catch (IOException exIoException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultProjects");
    }

    public void consultMembers() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultMember")) {
            try {
                ScreenController.instance.addScreen("consultMember", FXMLLoader.load(getClass().getResource("FXML/ConsultMemberFXML.fxml")));
            } catch (IOException ioException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultMember");
    }



}