package SGP.CA.GUI;

import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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


    private ArrayList<Member> allMembers = new ArrayList<>();
    MemberDAO memberDAO = new MemberDAO();

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
    }

    public void populateTable(){
        ArrayList<Member> allActiveMembers = new ArrayList<>();
        try {
            allMembers = memberDAO.getAllMembers();
        } catch (SQLException sqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        }
        for (int i = 0; i < allMembers.size(); i++) {
            if (allMembers.get(i).getActive() == 1) {
                allActiveMembers.add(allMembers.get(i));
            }
        }
        membersTable.getItems().setAll(allActiveMembers);
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

    public void registerMember() {
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        try {
            sceneSwitcher.createDialog((Stage) membersTable.getScene().getWindow(), "FXML/RegisterMemberFXML.fxml");
        } catch (IOException ioException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
        }
    }

    public Member getSelectedMember() {
        Member.selectedMember = membersTable.getSelectionModel().getSelectedItem();
        return Member.selectedMember;
    }


}
