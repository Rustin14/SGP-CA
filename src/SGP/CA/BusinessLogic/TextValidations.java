package SGP.CA.BusinessLogic;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

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













}
