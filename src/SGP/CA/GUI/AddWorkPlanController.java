package SGP.CA.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import SGP.CA.Domain.WorkPlan;
import SGP.CA.DataAccess.WorkPlanDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWorkPlanController extends Application {

    @FXML
    private TextField workPlanKeyTextField;

    @FXML
    private TextField startDateTextField;

    @FXML
    private TextField endDateTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/AddWorkPlanFXML.fxml"));
        primaryStage.setTitle("Registrar plan de trabajo");
        primaryStage.setScene(new Scene(root, 600, 200));
        primaryStage.show();
    }

    public void cancelButtonEvent(){
        Platform.exit();
        System.exit(0);
        //TODO
    }

    public void saveButtonEvent() throws ParseException, SQLException{
        WorkPlan workPlan = new WorkPlan();
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        workPlan.setWorkPlanKey(workPlanKeyTextField.getText());
        String stringStartDate = startDateTextField.getText();
        Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringStartDate);
        workPlan.setStartDate(startDate);
        String stringEndDate = endDateTextField.getText();
        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringEndDate);
        workPlan.setEndingDate(endDate);
        int result = workPlanDAO.saveWorkPlan(workPlan);
        if (result == 1){
            //TODO
            System.out.println("Registrado");
        }else{
            //TODO
            System.out.println("Error");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
