package SGP.CA.BusinessLogic;

import SGP.CA.Domain.Event;
import SGP.CA.Domain.Evidence;
import SGP.CA.Domain.Member;
import SGP.CA.GUI.AlertBuilder;
import SGP.CA.GUI.SceneSwitcher;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ProjectUtilities {

    public static void searchBar(TextField searchBar, TableView objectsTable, ObservableList<?> objectList) {
        FilteredList<?> filteredData = new FilteredList<>(objectList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(object -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (object.getClass().equals(new Event().getClass())) {
                    Event event = (Event) object;
                    if (event.getEventName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                    } else {
                        return false; // Does not match.
                    }
                } else if (object.getClass().equals(new Member().getClass())) {
                    Member member = (Member) object;
                    if (member.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                    } else {
                        return false; // Does not match.
                    }
                } else if (object.getClass().equals(new Evidence().getClass())) {
                    Evidence evidence = (Evidence) object;
                    if (evidence.getEvidenceTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                    } else {
                        return false; // Does not match.
                    }
                }
                return false;
            });
        });
        SortedList<Object> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(objectsTable.comparatorProperty());
        objectsTable.setItems(sortedData);
    }

    public static void setTextLimit (TextField searchBar, int maxCharacters) {
        searchBar.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= maxCharacters ? change : null));
    }

    public static void openDataModal(TableView objectTable, String path) {
        objectTable.setRowFactory( tv -> {
            TableRow<?> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    if (row.getItem().getClass().equals(new Event().getClass())) {
                        Event.selectedEvent = (Event) row.getItem();
                    } else if (row.getItem().getClass().equals(new Member().getClass())) {
                        Member.selectedMember = (Member) row.getItem();
                    } else if (row.getItem().getClass().equals(new Evidence().getClass())) {
                        Evidence.selectedEvidence = (Evidence) row.getItem();
                    }
                    SceneSwitcher sceneSwitcher = new SceneSwitcher();
                    try {
                        sceneSwitcher.createDialog((Stage) objectTable.getScene().getWindow(), path);
                    } catch (IOException exIoException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        alertBuilder.exceptionAlert("Error cargando la ventana. Intente de nuevo.");
                    }
                }
            });
            return row;
        });
    }

    public static void setArrayTextLimit(List<TextField> textFields, int maxCharacters) {
        for (TextField field : textFields) {
            field.setTextFormatter(new TextFormatter<String>(change ->
                    change.getControlNewText().length() <= maxCharacters ? change : null));
        }
    }

    public static void setMinimumDate(DatePicker datePicker) {
        LocalDate minimumDate = LocalDate.now();
        datePicker.setDayCellFactory(date ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(minimumDate));
                    }});
    }

    public static void setMaxDate(DatePicker datePicker) {
        LocalDate maxDate = LocalDate.now();
        datePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate));
                    }});
    }



}
