package SGP.CA.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import SGP.CA.Domain.BluePrint;
import SGP.CA.DataAccess.BluePrintDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddBluePrintController extends Application{

    @FXML
    private Button saveButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField bluePrintTitleField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField lgacField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField coDirectorFIeld;

    @FXML
    private TextField durationField;

    @FXML
    private TextField modalityField;

    @FXML
    private TextField studentField;

    @FXML
    private TextField requirementsTextField;

    @FXML
    private TextField receptionWorkName;

    @FXML
    private TextField directorTextField;

        @Override
        public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/AddBluePrintFXML.fxml"));
            primaryStage.setTitle("Registrar anteproyecto");
            primaryStage.setScene(new Scene(root, 900, 600));
            primaryStage.show();
        }

        public void exitButtonEvent (ActionEvent event) throws IOException{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ExitSaveProjectAlertFXML.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(exitButton.getScene().getWindow());
            stage.showAndWait();
        }

        public void saveButtonEvent(ActionEvent event)throws ParseException, SQLException, ClassNotFoundException, IOException {
            BluePrint bluePrint = new BluePrint();
            BluePrintDAO bluePrintDAO = new BluePrintDAO();
            bluePrint.setBluePrintTitle(bluePrintTitleField.getText());
            bluePrint.setDescription(descriptionField.getText());
            String stringStartDate = startDateField.getText();
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringStartDate);
            bluePrint.setStartDate(startDate);
            bluePrint.setCoDirector(coDirectorFIeld.getText());
            bluePrint.setDuration(Integer.parseInt(durationField.getText()));
            bluePrint.setModality(modalityField.getText());
            bluePrint.setStudent(studentField.getText());
            bluePrint.setAssociatedLgac(lgacField.getText());
            bluePrint.setState(stateField.getText());
            bluePrint.setRequirements(requirementsTextField.getText());
            bluePrint.setReceptionWorkName(receptionWorkName.getText());
            bluePrint.setDirector(directorTextField.getText());
            int action = bluePrintDAO.saveBluePrint(bluePrint);
            if (action == 1){
                showConfirmationAlert();
            }else{
                System.out.println("No ha sido posible guardar");
            }
        }

    public void showConfirmationAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/SaveBluePrintConfirmationAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

        public static void main(String[] args) {
            launch(args);
        }
}

