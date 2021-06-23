package SGP.CA.GUI;

import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModalParticipantsController implements Initializable {

    @FXML
    TableView availableMembersTable;
    @FXML
    TableColumn memberNameColumn;
    @FXML
    TableColumn firstLastNameColumn;
    @FXML
    TableColumn secondLastNameColumn;
    @FXML
    TableView selectedMembersTable;
    @FXML
    TableColumn selectedMemberNameColumn;
    @FXML
    TableColumn selectedFirstLastNameColumn;
    @FXML
    TableColumn selectedSecondLastNameColumn;

    MemberDAO memberDAO = new MemberDAO();
    ArrayList<Member> allMembers = new ArrayList<>();
    ArrayList<Member> allActiveMembers = new ArrayList<>();
    ArrayList<Member> selectedMembers = new ArrayList<>();
    Member selectedMember;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
        setSelectedListView();
        selectMember();
        deselectMember();
    }

    public void populateMembersTable() {
        try {
            allMembers = memberDAO.getAllMembers();
        } catch (SQLException sqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            sqlException.printStackTrace();
        }
        for (int i = 0; i < allMembers.size(); i++) {
            if (allMembers.get(i).getActive() == 1) {
                allActiveMembers.add(allMembers.get(i));
            }
        }
        availableMembersTable.getItems().setAll(allActiveMembers);
    }

    public void setListView() {
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
        firstLastNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("firstLastName"));
        secondLastNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("secondLastName"));
        populateMembersTable();
    }

    public void setSelectedListView() {
        selectedMemberNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
        selectedFirstLastNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("firstLastName"));
        selectedSecondLastNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("secondLastName"));
    }

    public void displayMemberOnSelectionsTable(Member selectedMember) {
        selectedMembers.add(selectedMember);
        allActiveMembers.remove(selectedMember);
        availableMembersTable.getItems().setAll(allActiveMembers);
        selectedMembersTable.getItems().setAll(selectedMembers);
    }

    public void removeMemberFromSelectionsTable(Member selectedMember) {
        selectedMembers.remove(selectedMember);
        allActiveMembers.add(selectedMember);
        availableMembersTable.getItems().setAll(allActiveMembers);
        selectedMembersTable.getItems().setAll(selectedMembers);
    }

    public void selectMember() {
        availableMembersTable.setRowFactory( tv -> {
            TableRow<Member> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    selectedMember = row.getItem();
                    displayMemberOnSelectionsTable(selectedMember);
                }
            });
            return row;
        });
    }

    public void deselectMember() {
        selectedMembersTable.setRowFactory( tv -> {
            TableRow<Member> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    selectedMember = row.getItem();
                    removeMemberFromSelectionsTable(selectedMember);
                }
            });
            return row;
        });
    }


}
