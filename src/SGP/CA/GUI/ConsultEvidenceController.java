package SGP.CA.GUI;

import SGP.CA.DataAccess.EvidenceDAO;
import SGP.CA.Domain.Evidence;
import SGP.CA.Domain.Member;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultEvidenceController implements Initializable {

    @FXML
    TableView evidenceTable;
    @FXML
    TableColumn evidenceTitleColumn;
    @FXML
    TableColumn evidenceTypeColumn;
    @FXML
    TextField searchBar;

    private static ConsultEvidenceController consultEvidenceControllerInstance;

    public ConsultEvidenceController () {
        consultEvidenceControllerInstance = this;
    }

    public static ConsultEvidenceController getInstance() {
        return consultEvidenceControllerInstance;
    }

    EvidenceDAO evidenceDAO = new EvidenceDAO();
    ArrayList<Evidence> allEvidence = new ArrayList<Evidence>();
    ObservableList<Evidence> evidenceList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
        openEvidenceDataModal();
        searchEvidence();
    }

    public void populateTable() {
        ArrayList<Evidence> allActiveEvidences = new ArrayList<>();
        ObservableList<Evidence> auxiliarEvidenceList = FXCollections.observableArrayList();

        try {
            allEvidence = evidenceDAO.getAllEvidence();
        } catch (SQLException sqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            sqlException.printStackTrace();
        }
        for (int i = 0; i < allEvidence.size(); i++) {
            if (allEvidence.get(i).getActive() == 1) {
                allActiveEvidences.add(allEvidence.get(i));
                auxiliarEvidenceList.add(allEvidence.get(i));
            }
        }
        evidenceList = auxiliarEvidenceList;
        searchEvidence();
    }

    public void registerEvidence() {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        try {
            sceneSwitcher.createDialog((Stage) evidenceTable.getScene().getWindow(), "FXML/RegisterEvidenceFXML.fxml");
        } catch (IOException ioException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        }
    }

    public void setListView() {
        evidenceTitleColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("evidenceTitle"));
        evidenceTypeColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("evidenceType"));
        populateTable();
    }

    public void openEvidenceDataModal() {
        evidenceTable.setRowFactory( tv -> {
            TableRow<Evidence> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Evidence.selectedEvidence = row.getItem();
                    SceneSwitcher sceneSwitcher = new SceneSwitcher();
                    try {
                        sceneSwitcher.createDialog((Stage) evidenceTable.getScene().getWindow(), "FXML/ModalConsultEvidenceFXML.fxml");
                    } catch (IOException ioException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        alertBuilder.exceptionAlert("Error cargando la ventana. Intente de nuevo.");
                        ioException.printStackTrace();
                    }
                }
            });
            return row ;
        });
    }

    public void searchEvidence() {
        FilteredList<Evidence> filteredData = new FilteredList<>(evidenceList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(evidence -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (evidence.getEvidenceTitle().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Evidence> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(evidenceTable.comparatorProperty());
        evidenceTable.setItems(sortedData);
    }

    public void consultEvents() {
        AlertBuilder alertBuilder = new AlertBuilder();
        if(!ScreenController.instance.isScreenOnMap("consultEvent")) {
            try {
                ScreenController.instance.addScreen("consultEvent", FXMLLoader.load(getClass().getResource("FXML/ConsultEventsFXML.fxml")));
            } catch (IOException ioException) {
                alertBuilder.exceptionAlert("No es posible acceder a la ventana. Intente de nuevo.");
            }
        }
        ScreenController.instance.activate("consultEvent");
    }

    public void goToProfile() {
        ScreenController.instance.activate("memberProf");
    }



}
