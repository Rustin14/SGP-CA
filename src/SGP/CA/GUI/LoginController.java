package SGP.CA.GUI;

import SGP.CA.BusinessLogic.HashPasswords;
import SGP.CA.DataAccess.ConnectDB;
import SGP.CA.DataAccess.MemberDAO;
import SGP.CA.Domain.Member;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField emailTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Button loginButton;

    ArrayList<Member> allActiveMembers = new ArrayList<>();
    MemberDAO memberDAO = new MemberDAO();
    boolean exceptionOcurred = false;
    Scene currentScene;
    ScreenController screenController;


    public LoginController()  {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getActiveMembers();
        setTextLimit();
    }

    public void getActiveMembers() {
        ArrayList<Member> allMembers = new ArrayList<>();
        try {
            allMembers = memberDAO.getAllMembers();
        } catch (SQLException exSqlException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible conectarse a la base de datos. Intente más tarde.");
            exceptionOcurred = true;
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
            }
        }
    }

    public void setTextLimit() {
        final int MAX_CHARS = 252;
        List<TextField> textFields = Arrays.asList(emailTextField, passwordTextField);
        for (TextField field : textFields) {
            field.setTextFormatter(new TextFormatter<String>(change ->
                    change.getControlNewText().length() <= MAX_CHARS ? change : null));
        }
    }

    public void signIn() {
        if (!exceptionOcurred) {
            Member member = new Member();
            HashPasswords hashPasswords = new HashPasswords();
            boolean goodEmail = false;
            boolean goodPassword = false;
            for (int i = 0; i < allActiveMembers.size(); i++) {
                if (allActiveMembers.get(i).getEmail().equals(emailTextField.getText())) {
                    goodEmail = true;
                    if (hashPasswords.isValid(passwordTextField.getText(), allActiveMembers.get(i).getPassword())) {
                        member = allActiveMembers.get(i);
                        Member.signedMember = allActiveMembers.get(i);
                        goodPassword = true;
                        break;
                    }
                }
            }
            if (goodEmail && goodPassword) {
                if (member.getIsResponsible() == 1) {
                    signedResponsable();
                } else if (member.getIsResponsible() == 0) {
                    signedMember();
                }
            }
            if (!goodEmail) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.errorAlert("No se encuentran coincidencias con el email introducido.");
            } else if (!goodPassword) {
                AlertBuilder alertBuilder = new AlertBuilder();
                alertBuilder.errorAlert("Contraseña equivocada.");
            }
        } else {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.errorAlert("El acceso a la base de datos está limitado, intente más tarde.");
        }
    }

    public void signedResponsable()  {
        currentScene = loginButton.getScene();
        screenController = new ScreenController(currentScene);
        try {
            screenController.addScreen("login", FXMLLoader.load(getClass().getResource("FXML/Login.fxml")));
            screenController.addScreen("responsibleProf", FXMLLoader.load(getClass().getResource("FXML/ResponsibleProfileFXML.fxml")));
        } catch (IOException ioException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible abrir la ventana.");
        }
        screenController.activate("responsibleProf");
    }

    public void signedMember() {
        currentScene = loginButton.getScene();
        screenController = new ScreenController(currentScene);
        try {
            screenController.addScreen("login", FXMLLoader.load(getClass().getResource("FXML/Login.fxml")));
            screenController.addScreen("memberProf", FXMLLoader.load(getClass().getResource("FXML/MemberProfileFXML.fxml")));
        } catch (IOException exIoException) {
            AlertBuilder alertBuilder = new AlertBuilder();
            alertBuilder.exceptionAlert("No es posible abrir la ventana.");
        }
        screenController.activate("memberProf");
    }

    




}
