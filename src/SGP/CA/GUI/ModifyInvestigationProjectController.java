package SGP.CA.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.application.Application;
import SGP.CA.Domain.InvestigationProject;
import SGP.CA.DataAccess.InvestigationProjectDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModifyInvestigationProjectController extends Application{

    @FXML
    private ComboBox<String> projectsTitleComboBox;

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyInvestigationProjectFXML.fxml"));
        primaryStage.setTitle("Modificar proyecto");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void saveButtonEvent (ActionEvent event){
        System.out.println("Save button pressed");
    }

    public void exitButtonEvent(ActionEvent event){
        System.out.println("Exit button pressed");
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        ArrayList<InvestigationProject> allProjects = investigationProjectDAO.getAllInvestigationProjects();
        ObservableList<String> allProjectsTile = FXCollections.observableArrayList();
        for (int i=0; i< allProjects.size(); i++){
            allProjectsTile.add(allProjects.get(i).getProjectTitle());
        }
        projectsTitleComboBox.setItems(allProjectsTile);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
