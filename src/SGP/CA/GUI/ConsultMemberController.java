package SGP.CA.GUI;

import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultMemberController implements Initializable {

    @FXML
    private ListView membersListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
    }

    public void setListView() {

        ObservableList<Member> data = FXCollections.observableArrayList();
        MemberDAO memberDAO = new MemberDAO();

        ArrayList<Member> allMembers = new ArrayList<>();
        try {
            allMembers = memberDAO.getAllMembers();
        } catch (SQLException sqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible acceder a la base de datos. Intente más tarde.");
        }

        for (int i = 0; i < allMembers.size(); i++) {
            data.addAll(allMembers.get(i));
        }

        membersListView.setItems(data);
        membersListView.setCellFactory(new Callback<ListView<Member>, ListCell<Member>>() {
            @Override
            public ListCell<Member> call(ListView<Member> listView) {
                return new MemberCell();
            }
        });
    }


}
