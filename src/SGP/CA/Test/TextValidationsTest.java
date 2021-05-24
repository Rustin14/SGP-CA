package SGP.CA.Test;

import SGP.CA.BusinessLogic.TextValidations;
import org.junit.Assert;
import org.junit.Test;

public class TextValidationsTest {

    TextValidations textValidations = new TextValidations();

    @Test
    public void validateCURPFormatPass () {
        String CURP = "FOLC970619HVZLRR08";
        boolean validatedCURP = textValidations.validateCURPFormat(CURP);

        Assert.assertEquals(true, validatedCURP);
    }

    @Test
    public void validateCURPFormatFail () {
        String CURP = "FOL970619HVZLRR08";
        boolean validatedCURP = textValidations.validateCURPFormat(CURP);

        Assert.assertEquals(false, validatedCURP);
    }

    @Test
    public void confirmPasswordPass () {
        String password = "password";
        String confirmedPassword = "password";
        boolean passwordValidated = textValidations.confirmPassword(password, confirmedPassword);

        Assert.assertEquals(true, passwordValidated);
    }

    @Test
    public void confirmPasswordFail () {
        String password = "password";
        String confirmedPassword = "pasword";
        boolean passwordValidated = textValidations.confirmPassword(password, confirmedPassword);

        Assert.assertEquals(false, passwordValidated);
    }
}
