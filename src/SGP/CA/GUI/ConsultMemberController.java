package SGP.CA.GUI;

import SGP.CA.BusinessLogic.ProjectUtilities;
import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.MemberDAO;
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
    @FXML
    private Label profileLabel;
    @FXML
    private Label evidencesLabel;
    @FXML
    private Label eventsLabel;
    @FXML
    private Label workPlanLabel;
    @FXML
    private Label projectsLabel;


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
        ProjectUtilities.openDataModal(membersTable, "FXML/ModalMemberData.fxml");
        ProjectUtilities.searchBar(searchBar, membersTable, memberList);
        ProjectUtilities.setTextLimit(searchBar, 50);
        setLabelActions();
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
        ProjectUtilities.searchBar(searchBar, membersTable, memberList);
    }
    public void setListView() {
        staffNumberColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("idMember"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
        firstLastNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("firstLastName"));
        secondLastNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("secondLastName"));
        populateTable();
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

    public void setLabelActions() {
        profileLabel.setOnMouseClicked(event -> SceneSwitcher.goToResponsibleProfile());
        evidencesLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleEvidences());
        eventsLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleEvents());
        workPlanLabel.setOnMouseClicked(event -> SceneSwitcher.consultWorkPlan());
        projectsLabel.setOnMouseClicked(event -> SceneSwitcher.consultResponsibleProjects());
    }


}
