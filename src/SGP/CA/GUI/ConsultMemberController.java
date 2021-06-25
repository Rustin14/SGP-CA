package SGP.CA.GUI;

import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.MemberDAO;
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
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultMemberController implements Initializable {

    @FXML
    private TableView<Member> membersTable;
    @FXML
    private TableColumn<Member, String> staffNumberColumn;
    @FXML
    private TableColumn<Member, String> memberNameColumn;
    @FXML
    private TableColumn<Member, String> firstLastNameColumn;
    @FXML
    private TableColumn<Member, String> secondLastNameColumn;
    @FXML
    private TextField searchBar;


    private ArrayList<Member> allMembers = new ArrayList<>();
    MemberDAO memberDAO = new MemberDAO();
    ObservableList<Member> memberList = FXCollections.observableArrayList();


    private static ConsultMemberController consultMemberControllerInstance;

    public ConsultMemberController() {
        consultMemberControllerInstance = this;
    }

    public static ConsultMemberController getInstance() {
        return consultMemberControllerInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
        openMemberDataModal();
        searchMember();
        setTextLimit();
    }

    public void populateTable(){
        ArrayList<Member> allActiveMembers = new ArrayList<>();
        ObservableList<Member> auxiliarMemberList = FXCollections.observableArrayList();
        try {
            allMembers = memberDAO.getAllMembers();
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
        for (int i = 0; i < allMembers.size(); i++) {
            if (allMembers.get(i).getActive() == 1) {
                allActiveMembers.add(allMembers.get(i));
                auxiliarMemberList.add(allMembers.get(i));
            }
        }
        memberList = auxiliarMemberList;
        searchMember();
    }
    public void setTextLimit () {
        final int MAX_CHARS = 50;
        searchBar.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));
    }

    public void setListView() {
        staffNumberColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("idMember"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
        firstLastNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("firstLastName"));
        secondLastNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("secondLastName"));
        populateTable();
    }

    public void openMemberDataModal() {
        membersTable.setRowFactory( tv -> {
            TableRow<Member> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Member.selectedMember = row.getItem();
                    SceneSwitcher sceneSwitcher = new SceneSwitcher();
                    try {
                        sceneSwitcher.createDialog((Stage) membersTable.getScene().getWindow(), "FXML/ModalMemberData.fxml");
                    } catch (IOException exIoException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        alertBuilder.exceptionAlert("Error cargando la ventana. Intente de nuevo.");
                    }
                }
            });
            return row ;
        });
    }

    public void registerMember() {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        try {
            sceneSwitcher.createDialog((Stage) membersTable.getScene().getWindow(), "FXML/RegisterMemberFXML.fxml");
        } catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        }
    }

    public void searchMember() {
        FilteredList<Member> filteredData = new FilteredList<>(memberList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (member.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Member> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(membersTable.comparatorProperty());
        membersTable.setItems(sortedData);
    }

    public void goToResponsibleProfile() {
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


    public void consultEvidences() {
        if(!ScreenController.instance.isScreenOnMap("consultEvidence")) {
            try {
                ScreenController.instance.addScreen("consultEvidence", FXMLLoader.load(getClass().getResource("FXML/ConsultEvidenceResponsibleFXML.fxml")));
            } catch (IOException exIoException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultEvidence");
    }

    public void consultEvents() {
        if(!ScreenController.instance.isScreenOnMap("consultEvents")) {
            try {
                ScreenController.instance.addScreen("consultEvents", FXMLLoader.load(getClass().getResource("FXML/ConsultEventsResponsibleFXML.fxml")));
            } catch (IOException exIoException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.exceptionAlert("No es posible acceder a la ventana.");
            }
        }
        ScreenController.instance.activate("consultEvents");
    }


}
