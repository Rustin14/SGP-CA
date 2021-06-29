package SGP.CA.GUI;

import SGP.CA.BusinessLogic.TextValidations;
import SGP.CA.DataAccess.ObjectiveDAO;
import SGP.CA.DataAccess.StrategyDAO;
import SGP.CA.Domain.Objective;
import SGP.CA.Domain.Strategy;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ModifyObjectiveController extends Application {

    ModifyWorkPlanController modifyWorkPlanController;

    @FXML
    private TextField objectiveTitleTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox<String> strategyComboBox;

    @FXML
    private TextField displayNumberTextField;

    @FXML
    private TextField displayStrategyTextField;

    @FXML
    private TextField displayGoalTextField;

    @FXML
    private TextField displayActionTextField;

    @FXML
    private TextField displayResultTextField;

    @FXML
    private TextField addNumberTextField;

    @FXML
    private TextField addStrategyTextField;

    @FXML
    private TextField addGoalTextField;

    @FXML
    private TextField addActionTextField;

    @FXML
    private TextField addResultTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    private ArrayList<Strategy> strategies = new ArrayList<>();

    private Objective objectiveToModify;

    private ObservableList<String> strategyTitles = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ModifyObjectiveFXML.fxml"));
            primaryStage.setTitle("Edicion");
            primaryStage.setScene(new Scene(root, 900, 600));
        }catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No se cargo correctamente el componente del sistema";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        primaryStage.show();
    }

    public void cancelButtonEvent() {
        AlertBuilder alertBuilder = new AlertBuilder();
        boolean confirmationMessage = alertBuilder.confirmationAlert("¿Estas seguro que desea salir?");
        if (confirmationMessage) {
            Stage stagePrincipal = (Stage) cancelButton.getScene().getWindow();
            stagePrincipal.close();
        }
    }

    public void getObjective(ModifyWorkPlanController modifyWorkPlanController, String objectiveTitle) {
        this.modifyWorkPlanController = modifyWorkPlanController;
        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
        Objective objective = new Objective();
        try {
            objective = objectiveDAO.searchObjectiveByTitle(objectiveTitle);
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        objectiveTitleTextField.setText(objective.getObjectiveTitle());
        descriptionTextArea.setText(objective.getDescription());
        objectiveToModify = objective;
        getAllStrategies();
    }

    public void deleteButtonEvent() {
        int indexSelected = strategyComboBox.getSelectionModel().getSelectedIndex();
        if (strategyTitles.size() == 1) {
            try {
                showInvalidDeleteStrategyAlert();
            }catch (IOException exIoException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
        }else {
            if (indexSelected == 0) {
                ObservableList<String> auxiliaryList = FXCollections.observableArrayList();
                ArrayList<Strategy> auxiliaryStrategiesList = new ArrayList<>();
                for (int i=1; i<strategyTitles.size(); i++) {
                    auxiliaryList.add(strategyTitles.get(i));
                    auxiliaryStrategiesList.add(strategies.get(i));
                }
                strategyTitles = auxiliaryList;
                strategies = auxiliaryStrategiesList;
            }else {
                strategyTitles.remove(indexSelected);
                strategies.remove(indexSelected);
            }
            strategyComboBox.setItems(strategyTitles);
            displayNumberTextField.clear();
            displayStrategyTextField.clear();
            displayGoalTextField.clear();
            displayActionTextField.clear();
            displayResultTextField.clear();
            strategyComboBox.getSelectionModel().clearSelection();
        }
    }

    public void addButtonEvent() {
        String noEmptyFields = checkEmptyStrategyTextFields();
        if (!noEmptyFields.equals("noEmptyTextFields")) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No has llenado el campo "+ noEmptyFields;
            alertBuilder.exceptionAlert(exceptionMessage);
        }else {
            String noExceededTextLimit = checkStrategyTextLimit();
            if (!noExceededTextLimit.equals("allLimitsRespected")) {
                AlertBuilder alertBuilder = new AlertBuilder();
                String errorMessage = "Limite de texto excedido en el campo "+ noExceededTextLimit;
                alertBuilder.errorAlert(errorMessage);
            }else {
                String validTextFields = valideStrategyTextFields();
                if (!validTextFields.equals("allFieldsAreValid")) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String errorMessage;
                    if (validTextFields.equals("Numero")){
                        errorMessage = "Solo requieres de numeros enteros para el campo " + validTextFields;
                    }else{
                        errorMessage = "Solo requieres de letras en el campo " + validTextFields;
                    }
                    alertBuilder.errorAlert(errorMessage);
                }else {
                    Strategy strategy = new Strategy();
                    strategy.setNumber(Integer.parseInt(addNumberTextField.getText()));
                    strategy.setStrategy(addStrategyTextField.getText());
                    strategy.setGoal(addGoalTextField.getText());
                    strategy.setAction(addActionTextField.getText());
                    strategy.setResult(addResultTextField.getText());
                    if (!strategies.contains(strategy)){
                        strategies.add(strategy);
                        strategyTitles.add(strategy.getStrategy());
                        strategyComboBox.setItems(strategyTitles);
                    }
                }
            }
        }
    }

    public void saveButtonEvent() {
        if (objectiveTitleTextField.getText().isEmpty() || descriptionTextArea.getText().isEmpty()){
            try {
                showMissingInformationAlert();
            }catch (IOException exIoException) {
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                alertBuilder.exceptionAlert(exceptionMessage);
            }
        }else {
            String noExceededLimitText = checkObjectiveTextLimit();
            if (!noExceededLimitText.equals("AllLimitsRespected")) {
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "Limite de texto excedido en el campo "+noExceededLimitText;
                alertBuilder.exceptionAlert(exceptionMessage);
            }else {
                String validObjectiveTextFields = validateObjectiveTextFields();
                if (!validObjectiveTextFields.equals("AllFieldsAreValid")) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String exceptionMessage = "Solo debes ingresar letras en el campo "+validObjectiveTextFields;
                    alertBuilder.exceptionAlert(exceptionMessage);
                }else {
                    ObjectiveDAO objectiveDAO = new ObjectiveDAO();
                    StrategyDAO strategyDAO = new StrategyDAO();
                    Objective objective = new Objective();
                    objective.setObjectiveTitle(objectiveTitleTextField.getText());
                    objective.setDescription(descriptionTextArea.getText());
                    int resultObjectiveDAO = 0;
                    int resultStrategyDAO = 0;
                    try {
                        for (int i=0; i< strategies.size(); i++) {
                            strategyDAO.deleteStrategy(strategies.get(i).getStrategy());
                        }
                    }catch (SQLException exSqlException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                    int resultDeleteObjectiveDAO = 0;
                    try {
                        resultDeleteObjectiveDAO = objectiveDAO.deleteObjective(objectiveToModify.getObjectiveTitle());
                    }catch (SQLException exSqlException) {
                        AlertBuilder alertBuilder = new AlertBuilder();
                        String exceptionMessage = "Ocurrio un error inesperado en la base de datos";
                        alertBuilder.exceptionAlert(exceptionMessage);
                    }
                    if (resultDeleteObjectiveDAO >= 1) {
                        try {
                            for (int i=0; i< strategies.size(); i++) {
                                objective.setStrategy(strategies.get(i).getStrategy());
                                resultObjectiveDAO += objectiveDAO.saveObjective(objective);
                                resultStrategyDAO += strategyDAO.saveStrategy(strategies.get(i));
                            }
                        }catch (SQLException exSqlException) {
                            AlertBuilder alertBuilder = new AlertBuilder();
                            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
                            alertBuilder.exceptionAlert(exceptionMessage);
                        }
                        if (resultObjectiveDAO == strategies.size() && resultStrategyDAO == strategies.size()) {
                            try {
                                showSuccessfulUpdateAlert();
                                Stage stagePrincipal = (Stage) saveButton.getScene().getWindow();
                                stagePrincipal.close();
                            }catch (IOException exSoException) {
                                AlertBuilder alertBuilder = new AlertBuilder();
                                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                                alertBuilder.exceptionAlert(exceptionMessage);
                            }
                        }else {
                            try {
                                showFailedRegisterAlert();
                            }catch (IOException exIoException) {
                                AlertBuilder alertBuilder = new AlertBuilder();
                                String exceptionMessage = "No se cargo correctamente el componente del sistema";
                                alertBuilder.exceptionAlert(exceptionMessage);
                            }
                        }
                    }else {
                        try {
                            showFailedRegisterAlert();
                        }catch (IOException exIoException) {
                            AlertBuilder alertBuilder = new AlertBuilder();
                            String exceptionMessage = "No se cargo correctamente el componente del sistema";
                            alertBuilder.exceptionAlert(exceptionMessage);
                        }
                    }
                }
            }
        }
    }

    public void strategyComboBoxEvent() {
        String strategySelected = strategyComboBox.getSelectionModel().getSelectedItem();
        int indexSelected = strategyTitles.indexOf(strategySelected);
        Strategy strategy;
        try {
            if (indexSelected == 0) {
                strategy = strategies.get(0);
            }else {
                strategy = strategies.get(indexSelected);
            }
            displayNumberTextField.setText(String.valueOf(strategy.getNumber()));
            displayStrategyTextField.setText(strategy.getStrategy());
            displayGoalTextField.setText(strategy.getGoal());
            displayActionTextField.setText(strategy.getAction());
            displayResultTextField.setText(strategy.getResult());
        }catch (IndexOutOfBoundsException exIndexOutOfBoundsException){
            strategyComboBox.getSelectionModel().clearSelection();
            displayNumberTextField.clear();
            displayStrategyTextField.clear();
            displayGoalTextField.clear();
            displayActionTextField.clear();
            displayResultTextField.clear();
        }
    }

    public void modifyButtonEvent() {
        String noEmptyFields = checkEmptyStrategyModifiedTextFields();
        if (!noEmptyFields.equals("noEmptyTextFields")) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No has llenado el campo " + noEmptyFields;
            alertBuilder.exceptionAlert(exceptionMessage);
        }else {
            String noExceededLimitText = checkStrategyModifiedTextLimit();
            if (!noExceededLimitText.equals("allLimitsRespected")) {
                AlertBuilder alertBuilder = new AlertBuilder();
                String exceptionMessage = "Limite de texto excedido en el campo " + noExceededLimitText;
                alertBuilder.exceptionAlert(exceptionMessage);
            }else {
                String validTextFields = valideStrategyModifiedTextFields();
                if (!validTextFields.equals("allFieldsAreValid")) {
                    AlertBuilder alertBuilder = new AlertBuilder();
                    String errorMessage;
                    if (validTextFields.equals("Numero")){
                        errorMessage = "Solo requieres de numeros enteros en el campo " + validTextFields;
                    }else{
                        errorMessage = "Solo requieres de letras en el campo " + validTextFields;
                    }
                    alertBuilder.errorAlert(errorMessage);
                }else {
                    Strategy strategy = new Strategy();
                    strategy.setNumber(Integer.parseInt(displayNumberTextField.getText()));
                    strategy.setStrategy(displayStrategyTextField.getText());
                    strategy.setGoal(displayGoalTextField.getText());
                    strategy.setAction(displayActionTextField.getText());
                    strategy.setResult(displayResultTextField.getText());
                    if (!strategies.contains(strategy)) {
                        String strategySelected = strategyComboBox.getSelectionModel().getSelectedItem();
                        int indexSelected = strategyTitles.indexOf(strategySelected);
                        strategies.get(indexSelected).setNumber(strategy.getNumber());
                        strategies.get(indexSelected).setStrategy(strategy.getStrategy());
                        strategies.get(indexSelected).setGoal(strategy.getGoal());
                        strategies.get(indexSelected).setAction(strategy.getAction());
                        strategies.get(indexSelected).setResult(strategy.getResult());
                        strategyTitles.set(indexSelected, strategy.getStrategy());
                        strategyComboBox.setItems(strategyTitles);
                    }else {
                        try {
                            showNoStrategyModifiedAlert();
                        }catch (IOException exIoException) {
                            AlertBuilder alertBuilder = new AlertBuilder();
                            String exceptionMessage = "No se cargo correctamente el componente del sistema";
                            alertBuilder.exceptionAlert(exceptionMessage);
                        }
                    }
                }
            }
        }
    }

    public void getAllStrategies() {
        ObjectiveDAO objectiveDAO = new ObjectiveDAO();
        StrategyDAO strategyDAO = new StrategyDAO();
        ArrayList<Objective> objectives = new ArrayList<>();
        try {
            objectives = objectiveDAO.getAllObjectives();
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
        for (int i=0; i<objectives.size(); i++) {
            if (objectives.get(i).getObjectiveTitle().equals(objectiveTitleTextField.getText())) {
                strategyTitles.add(objectives.get(i).getStrategy());
            }
        }
        strategyComboBox.setItems(strategyTitles);
        try {
            for (int i=0; i< strategyTitles.size(); i++) {
                Strategy strategy = strategyDAO.searchStrategyByStrategy(strategyTitles.get(i));
                strategies.add(strategy);
            }
        }catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            String exceptionMessage = "No es posible acceder a la base de datos. Intente más tarde";
            alertBuilder.exceptionAlert(exceptionMessage);
        }
    }

    public void showSuccessfulUpdateAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/SuccessfulUpdateModifyObjectiveAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showMissingInformationAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/ObjectiveMissingInformationAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showFailedRegisterAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/FailedRegisterAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showInvalidDeleteStrategyAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/InvalidDeleteStrategyModifyObjectiveAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showNoStrategyModifiedAlert() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/NoStrategyModifiedAlertFXML.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());
        stage.showAndWait();
    }

    public String checkObjectiveTextLimit() {
        if (objectiveTitleTextField.getText().length() > 100) {
            return "Titulo del objetivo";
        }
        if (descriptionTextArea.getText().length() > 255) {
            return "Descripcion del objetivo";
        }
        return "AllLimitsRespected";
    }

    public String validateObjectiveTextFields() {
        if (!objectiveTitleTextField.getText().matches("[a-zA-Z\\s]*$")) {
            return "Titulo del objetivo";
        }
        if (!descriptionTextArea.getText().matches("[a-zA-Z\\s]*$")) {
            return "Descripcion del objetivo";
        }
        return "AllFieldsAreValid";
    }

    public String valideStrategyTextFields() {
        TextValidations textValidations = new TextValidations();
        ArrayList<String> numberFieldTexts = new ArrayList<>(Arrays.asList(addNumberTextField.getText()));
        ArrayList<String> numberFieldNames = new ArrayList<>(Arrays.asList("Numero"));
        TextField [] textFields = {addStrategyTextField, addGoalTextField, addActionTextField,
                addResultTextField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Estrategia", "Meta", "Accion", "Resultado"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String invalidTextField = textValidations.validateTextFields(textFieldTexts, textFieldNames);
        if (invalidTextField.equals("allFieldsAreValid")) {
            String invalidNumberField = textValidations.validateNumberFields(numberFieldTexts, numberFieldNames);
            if (!invalidNumberField.equals("allFieldsAreValid")) {
                return invalidNumberField;
            }
        }else{
            return invalidTextField;
        }
        return "allFieldsAreValid";
    }

    public String checkStrategyTextLimit() {
        TextValidations textValidations = new TextValidations();
        TextField [] textFields = {addStrategyTextField, addGoalTextField, addActionTextField, addResultTextField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Estrategia", "Meta", "Accion", "Resultado"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        int [] textLimits = {255, 100, 255, 255};
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String exceedLimitTextField =textValidations.checkTextFieldsLimits(textFieldTexts, textLimits, textFieldNames);
        if (!exceedLimitTextField.equals("allLimitsRespected")) {
            return exceedLimitTextField;
        }
        return "allLimitsRespected";
    }

    public String checkEmptyStrategyTextFields() {
        TextValidations textValidations = new TextValidations();
        TextField [] textFields = {addNumberTextField, addStrategyTextField, addGoalTextField, addActionTextField,
                addResultTextField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Numero", "Estrategia", "Meta", "Accion",
                "Resultado"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String emptyTextField = textValidations.checkNoEmptyTextFields(textFieldTexts, textFieldNames);
        if (!emptyTextField.equals("noEmptyTextFields")){
            return emptyTextField;
        }
        return "noEmptyTextFields";
    }

    public String valideStrategyModifiedTextFields() {
        TextValidations textValidations = new TextValidations();
        ArrayList<String> numberFieldTexts = new ArrayList<>(Arrays.asList(displayNumberTextField.getText()));
        ArrayList<String> numberFieldNames = new ArrayList<>(Arrays.asList("Numero"));
        TextField [] textFields = {displayStrategyTextField, displayGoalTextField, displayActionTextField,
                displayResultTextField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Estrategia", "Meta", "Accion", "Resultado"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String invalidTextField = textValidations.validateTextFields(textFieldTexts, textFieldNames);
        if (invalidTextField.equals("allFieldsAreValid")) {
            String invalidNumberField = textValidations.validateNumberFields(numberFieldTexts, numberFieldNames);
            if (!invalidNumberField.equals("allFieldsAreValid")) {
                return invalidNumberField;
            }
        }else{
            return invalidTextField;
        }
        return "allFieldsAreValid";
    }

    public String checkStrategyModifiedTextLimit() {
        TextValidations textValidations = new TextValidations();
        TextField [] textFields = {displayStrategyTextField, displayGoalTextField, displayActionTextField,
                displayResultTextField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Estrategia", "Meta", "Accion", "Resultado"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        int [] textLimits = {255, 100, 255, 255};
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String exceedLimitTextField =textValidations.checkTextFieldsLimits(textFieldTexts, textLimits, textFieldNames);
        if (!exceedLimitTextField.equals("allLimitsRespected")) {
            return exceedLimitTextField;
        }
        return "allLimitsRespected";
    }

    public String checkEmptyStrategyModifiedTextFields() {
        TextValidations textValidations = new TextValidations();
        TextField [] textFields = {displayNumberTextField, displayStrategyTextField, displayGoalTextField,
                displayActionTextField, displayResultTextField};
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("Numero", "Estrategia", "Meta", "Accion",
                "Resultado"));
        ArrayList<String> textFieldTexts = new ArrayList<>();
        for (int i=0; i<textFields.length; i++){
            textFieldTexts.add(textFields[i].getText());
        }
        String emptyTextField = textValidations.checkNoEmptyTextFields(textFieldTexts, textFieldNames);
        if (!emptyTextField.equals("noEmptyTextFields")){
            return emptyTextField;
        }
        return "noEmptyTextFields";
    }

    public static void main(String[] args) {
        launch(args);
    }

}