package SGP.CA.GUI;

import SGP.CA.Domain.BluePrint;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import SGP.CA.DataAccess.BluePrintDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConsultBluePrintController extends Application{

    InvestigationProjectConsultController investigationProjectConsultController;

    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField directorTextField;

    @FXML
    private TextField bluePrintTitleTextField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField associatedLGAC;

    @FXML
    private TextField stateTextField;

    @FXML
    private TextField coDirectorTextField;

    @FXML
    private TextField durationTextField;

    @FXML
    private TextField modalityTextField;

    @FXML
    private TextField receptionWorkNameTextField;

    @FXML
    private TextField requirementsTextField;

    @FXML
    private TextField studentTextField;

    @FXML
    private TextArea descriptionTextArea;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ConsultBluePrintFXML.fxml"));
        primaryStage.setTitle("Registrar anteproyecto");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public void deleteButtonEvent() throws SQLException, ClassNotFoundException {
        BluePrintDAO bluePrintDAO = new BluePrintDAO();
        int result = bluePrintDAO.deleteBluePrint(bluePrintTitleTextField.getText());
        if (result == 1){
            //TO DO
            System.out.println("Eliminado");
        }else{
            System.out.println("Fallido");
        }
    }

    public void modifyButtonEvent() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyBluePrintFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(modifyButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void getBluePrintTitle(InvestigationProjectConsultController stage2Controller, String bluePrintTitle) throws SQLException, ClassNotFoundException{
        bluePrintTitleTextField.setText(bluePrintTitle);
        investigationProjectConsultController = stage2Controller;
        searchProject();
    }


    public void searchProject() throws SQLException, ClassNotFoundException{
        BluePrintDAO bluePrintDAO = new BluePrintDAO();
        BluePrint bluePrint = bluePrintDAO.searchBluePrintByTitle(bluePrintTitleTextField.getText());
        DateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        String startDate = setDate.format(bluePrint.getStartDate());
        startDateField.setText(startDate);
        associatedLGAC.setText(bluePrint.getAssociatedLgac());
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

    public static void main(String[] args) {
        launch(args);
    }
}
