package SGP.CA.BusinessLogic;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class TextValidations {

    public boolean validatePhoneNumber(String phoneNumber) {
        String regexPhoneNumber = "^\\d{10}$";
        boolean validatedPhone = phoneNumber.matches(regexPhoneNumber);
        return  validatedPhone;
    }

    public boolean validateCURPFormat(String CURP) {
        String regexCURP = "^([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]" +
                "|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$";
        boolean validated = CURP.matches(regexCURP);
        return validated;
    }

    public boolean confirmPassword(String password, String secondPassword) {
        if(password.equals(secondPassword)) {
            return true;
        }
        return false;
    }

    public boolean validateHourFormat(String hour) {
        String regexHour = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        boolean validated = hour.matches(regexHour);
        return validated;
    }

    public String checkNoEmptyTextFields(ArrayList<String> textFields, ArrayList<String> textFieldNames) {
        for (int i=0; i< textFields.size(); i++) {
            if (textFields.get(i).isEmpty()) {
                return textFieldNames.get(i);
            }
        }
        return "noEmptyTextFields";
    }

    public String checkTextFieldsLimits(ArrayList<String> textFields, int [] textFieldBoundaries,
                                        ArrayList<String> textFieldNames) {
        for (int i=0; i<textFields.size(); i++){
            if(textFields.get(i).length() > textFieldBoundaries[i]){
                return textFieldNames.get(i);
            }
        }
        return "allLimitsRespected";
    }

    public String validateNumberFields(ArrayList<String> numberFields, ArrayList<String> numberFieldNames) {
        String regularExpression = "[0-9]*";
        int noMatchFieldIndex = -1;
        for (int i=0; i< numberFields.size(); i++){
            if (!numberFields.get(i).matches(regularExpression)){
                noMatchFieldIndex = i;
                break;
            }
        }
        if (noMatchFieldIndex != -1){
            return numberFieldNames.get(noMatchFieldIndex);
        }
        return "allFieldsAreValid";
    }

    public String validateTextFields(ArrayList<String> textFields, ArrayList<String> textFieldNames) {
        String regularExpression = "[a-zA-Z\\s]*$";
        int noMatchFieldIndex = -1;
        for (int i=0; i< textFields.size(); i++) {
            if (!textFields.get(i).matches(regularExpression)) {
                noMatchFieldIndex = i;
                break;
            }
        }
        if (noMatchFieldIndex != -1) {
            return textFieldNames.get(noMatchFieldIndex);
        }
        return "allFieldsAreValid";
    }

    public String checkNoEmptyDescription(String descriptionText){
        if (descriptionText.isEmpty()){
            return "Descripcion";
        }
        return "noEmptyField";
    }

    public String checkDescriptionFieldLimit(String descriptionArea){
        if (descriptionArea.length() > 255){
            return "Descripcion";
        }
        return "validField";
    }

    public String validateDescriptionField(String descriptionArea){
        String regularExpression = "[a-zA-Z\\s]*$";
        if (!descriptionArea.matches(regularExpression)){
            return "Descripcion";
        }
        return "ValidField";
    }






}
