package SGP.CA.GUI;

import SGP.CA.DataAccess.EventDAO;
import SGP.CA.Domain.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultEventsController implements Initializable {

    @FXML
    TableView eventsTable;
    @FXML
    TableColumn eventTypeColumn;
    @FXML
    TableColumn eventNameColumn;
    @FXML
    TableColumn dateColumn;

    private static ConsultEventsController instance;

    public ConsultEventsController getInstance() {
        return instance;
    }

    public ConsultEventsController () {
        instance = this;
    }

    EventDAO eventDAO = new EventDAO();
    ArrayList<Event> allEvents = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
    }

    public void populateTable() {
            ArrayList<Event> allActiveEvents = new ArrayList<>();
            try {
                allEvents = eventDAO.getAllEvent();
            } catch (SQLException sqlException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
                sqlException.printStackTrace();
            }
            for (int i = 0; i < allEvents.size(); i++) {
                if (allEvents.get(i).getActive() == 1) {
                    allActiveEvents.add(allEvents.get(i));
                }
            }
            eventsTable.getItems().setAll(allActiveEvents);
    }

    public void setListView() {
        eventTypeColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventType"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDate"));
        populateTable();
    }

}
