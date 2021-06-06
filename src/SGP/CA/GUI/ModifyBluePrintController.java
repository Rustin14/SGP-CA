package SGP.CA.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import SGP.CA.DataAccess.BluePrintDAO;
import SGP.CA.Domain.BluePrint;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ModifyBluePrintController extends Application {

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField receptionWorkNameTextField;

    @FXML
    private TextField bluePrintTitleTextField;

    @FXML
    private TextField starDateTextField;

    @FXML
    private TextField associatedLgacTextField;

    @FXML
    private TextField stateTextField;

    @FXML
    private TextField directorTextField;

    @FXML
    private TextField coDirectorTextField;

    @FXML
    private TextField durationTextField;

    @FXML
    private TextField modalityTextField;

    @FXML
    private TextField requirementsTextField;

    @FXML
    private TextField studentTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox<String> bluePrintsComboBox;

    private BluePrintDAO bluePrintDAO = new BluePrintDAO();

    private ArrayList<BluePrint> bluePrints = new ArrayList<>();

    private ObservableList<String> titles = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyBluePrintFXML.fxml"));
        primaryStage.setTitle("Modificar anteproyecto ");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void bluePrintSelectedEvent() throws SQLException, ClassNotFoundException {
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String titleSelected = bluePrintsComboBox.getSelectionModel().getSelectedItem();
        BluePrint bluePrint = bluePrintDAO.searchBluePrintByTitle(titleSelected);
        bluePrintTitleTextField.setText(bluePrint.getBluePrintTitle());
        associatedLgacTextField.setText(bluePrint.getAssociatedLgac());
        String startDate = setDate.format(bluePrint.getStartDate());
        starDateTextField.setText(startDate);
        stateTextField.setText(bluePrint.getState());
        directorTextField.setText(bluePrint.getDirector());
        coDirectorTextField.setText(bluePrint.getCoDirector());
        durationTextField.setText(String.valueOf(bluePrint.getDuration()));
        modalityTextField.setText(bluePrint.getModality());
        receptionWorkNameTextField.setText(bluePrint.getReceptionWorkName());
        requirementsTextField.setText(bluePrint.getRequirements());
        studentTextField.setText(bluePrint.getStudent());
        descriptionTextArea.setText(bluePrint.getDescription());
    }

    public void cancelButtonEvent() {
        System.out.println("TO DO");
        Platform.exit();
        System.exit(0);
    }

    public void saveButtonEvent() throws ParseException, SQLException,ClassNotFoundException {
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String titleSelected = bluePrintsComboBox.getSelectionModel().getSelectedItem();
        BluePrint bluePrint = new BluePrint();
        bluePrint.setBluePrintTitle(bluePrintTitleTextField.getText());
        String startDateString = starDateTextField.getText();
        Date startDate = setDate.parse(startDateString);
        bluePrint.setStartDate(startDate);
        bluePrint.setAssociatedLgac(associatedLgacTextField.getText());
        bluePrint.setState(stateTextField.getText());
        bluePrint.setDirector(directorTextField.getText());
        bluePrint.setCoDirector(coDirectorTextField.getText());
        bluePrint.setDuration(Integer.parseInt(durationTextField.getText()));
        bluePrint.setModality(modalityTextField.getText());
        bluePrint.setReceptionWorkName(receptionWorkNameTextField.getText());
        bluePrint.setRequirements(requirementsTextField.getText());
        bluePrint.setStudent(studentTextField.getText());
        bluePrint.setDescription(descriptionTextArea.getText());
        int result = bluePrintDAO.modifyBluePrint(bluePrint, titleSelected);
        if (result == 1){
            System.out.println("Modificacion exitosa");
            //TO DO
        }else{
            System.out.println("No se pudo modificar");
            //TO DO
        }
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        bluePrints = bluePrintDAO.getAllBluePrints();
        for (BluePrint blueprint : bluePrints){
            titles.add(blueprint.getBluePrintTitle());
        }
        bluePrintsComboBox.setItems(titles);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
