package SGP.CA.Test;

import SGP.CA.BusinessLogic.TextValidations;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

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

    @Test
    public void phoneValidationPassTest () {
        String phoneNumber = "2281987654";
        boolean phoneValidated = textValidations.validatePhoneNumber(phoneNumber);

        Assert.assertEquals(true, phoneValidated);
    }

    @Test
    public void phoneValidationFailTest () {
        String phoneNumber = "281987654";
        boolean phoneValidated = textValidations.validatePhoneNumber(phoneNumber);

        Assert.assertEquals(false, phoneValidated);
    }

    @Test
    public void hourFormatValidationPassTest () {
        String hour = "13:00";
        boolean hourFormatValidated = textValidations.validateHourFormat(hour);

        Assert.assertEquals(true, hourFormatValidated);
    }

    @Test
    public void hourFormatValidationFailTest () {
        String hour = "13::00";
        boolean hourFormatValidated = textValidations.validateHourFormat(hour);

        Assert.assertEquals(false, hourFormatValidated);
    }

    @Test
    public void checkNoEmptyTextFieldsPassTest () {
        ArrayList<String> textFieldTexts = new ArrayList<>(Arrays.asList("Text one", "Text two", "Text three",
                "Text four", "Text five", "Text six"));
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("TextField one", "TextField two",
                "TextField three", "TextField four", "TextField five", "TextField six"));
        String noEmptyTextFields = textValidations.checkNoEmptyTextFields(textFieldTexts, textFieldNames);

        Assert.assertEquals("noEmptyTextFields", noEmptyTextFields);
    }

    @Test
    public void checkNoEmptyTextFieldsFailTest () {
        ArrayList<String> textFieldTexts = new ArrayList<>(Arrays.asList("Text one", "", "Text three",
                "Text four", "Text five", "Text six"));
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("TextField one", "TextField two",
                "TextField three", "TextField four", "TextField five", "TextField six"));
        String noEmptyTextFields = textValidations.checkNoEmptyTextFields(textFieldTexts, textFieldNames);

        Assert.assertEquals("TextField two", noEmptyTextFields);
    }

    @Test
    public void checkTextFieldsLimitsPassTest () {
        ArrayList<String> textFieldTexts = new ArrayList<>(Arrays.asList("Text one", "Text two", "Text three",
                "Text four", "Text five", "Text six"));
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("TextField one", "TextField two",
                "TextField three", "TextField four", "TextField five", "TextField six"));
        int [] textLimits = {30, 30, 30, 30, 30, 30};
        String noExceededTextFields = textValidations.checkTextFieldsLimits(textFieldTexts, textLimits, textFieldNames);

        Assert.assertEquals("allLimitsRespected", noExceededTextFields);
    }

    @Test
    public void checkTextFieldsLimitsFailTest () {
        ArrayList<String> textFieldTexts = new ArrayList<>(Arrays.asList("Text one", "Text two", "Text three",
                "Text four", "Text five", "Text six"));
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("TextField one", "TextField two",
                "TextField three", "TextField four", "TextField five", "TextField six"));
        int [] textLimits = {30, 30, 30, 5, 30, 30};
        String noExceededTextFields = textValidations.checkTextFieldsLimits(textFieldTexts, textLimits, textFieldNames);

        Assert.assertEquals("TextField four", noExceededTextFields);
    }

    @Test
    public void validateTextFieldsPassTest () {
        ArrayList<String> textFieldTexts = new ArrayList<>(Arrays.asList("Text one", "Text two", "Text three",
                "Text four", "Text five", "Text six"));
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("TextField one", "TextField two",
                "TextField three", "TextField four", "TextField five", "TextField six"));
        String validFields = textValidations.validateTextFields(textFieldTexts, textFieldNames);

        Assert.assertEquals("allFieldsAreValid", validFields);
    }

    @Test
    public void validateTextFieldsFailTest () {
        ArrayList<String> textFieldTexts = new ArrayList<>(Arrays.asList("Text one", "Text two", "Text three",
                "Text four", "Text 54321", "Text six"));
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("TextField one", "TextField two",
                "TextField three", "TextField four", "TextField five", "TextField six"));
        String validFields = textValidations.validateTextFields(textFieldTexts, textFieldNames);

        Assert.assertEquals("TextField five", validFields);
    }

    @Test
    public void validateNumberFieldsPassTest () {
        ArrayList<String> textFieldTexts = new ArrayList<>(Arrays.asList("1234567", "7654321", "098765",
                "0192837", "106739674", "1987090700"));
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("TextField one", "TextField two",
                "TextField three", "TextField four", "TextField five", "TextField six"));
        String validFields = textValidations.validateNumberFields(textFieldTexts, textFieldNames);

        Assert.assertEquals("allFieldsAreValid", validFields);
    }

    @Test
    public void validateNumberFieldsFailTest () {
        ArrayList<String> textFieldTexts = new ArrayList<>(Arrays.asList("1234567", "7654321", "098765",
                "0192837", "106739674", "uno dos tres"));
        ArrayList<String> textFieldNames = new ArrayList<>(Arrays.asList("TextField one", "TextField two",
                "TextField three", "TextField four", "TextField five", "TextField six"));
        String validFields = textValidations.validateNumberFields(textFieldTexts, textFieldNames);

        Assert.assertEquals("TextField six", validFields);
    }

    @Test
    public void checkNoEmptyDescriptionPassTest () {
        String descriptionText = "Description text Test";
        String noEmptyDescription = textValidations.checkNoEmptyDescription(descriptionText);

        Assert.assertEquals("noEmptyField", noEmptyDescription);
    }

    @Test
    public void checkNoEmptyDescriptionFailTest () {
        String descriptionText = "";
        String noEmptyDescription = textValidations.checkNoEmptyDescription(descriptionText);

        Assert.assertEquals("Descripcion", noEmptyDescription);
    }

    @Test
    public void checkDescriptionFieldLimitPassTest () {
        String descriptionText = "Description text Test limit of length";
        String noEmptyDescription = textValidations.checkDescriptionFieldLimit(descriptionText);

        Assert.assertEquals("validField", noEmptyDescription);
    }

    @Test
    public void checkDescriptionFieldLimitFailTest () {
        String descriptionText = "Description Description Description Description Description Description Description" +
                " Description Description Description Description Description Description Description Description" +
                " Description Description Description Description Description Description Description Description";
        String noEmptyDescription = textValidations.checkDescriptionFieldLimit(descriptionText);

        Assert.assertEquals("Descripcion", noEmptyDescription);
    }

    @Test
    public void validateDescriptionFieldPassTest () {
        String descriptionText = "Description text Test limit of length";
        String noEmptyDescription = textValidations.validateDescriptionField(descriptionText);

        Assert.assertEquals("ValidField", noEmptyDescription);
    }

    @Test
    public void validateDescriptionFieldFailTest () {
        String descriptionText = "Description 123456789";
        String noEmptyDescription = textValidations.validateDescriptionField(descriptionText);

        Assert.assertEquals("Descripcion", noEmptyDescription);
    }
}
