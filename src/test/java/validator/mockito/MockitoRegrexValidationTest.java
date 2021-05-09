package validator.mockito;

import annotation.Regrex;
import annotation.validation.RegrexValidation;
import model.Staff;
import model.Violation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import validator.DefaultValidator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(MockitoJUnitRunner.class)
public class MockitoRegrexValidationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);

    private Collection<Violation> violations = new ArrayList<>();

    private RegrexValidation regrexValidation = new RegrexValidation();

    private Field field;

    private Field getField(String fieldName) {
        try {
            field  = Staff.class.getDeclaredField(fieldName);
            return field;
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.fillInStackTrace());
        }
        return null;
    }

    @Test
    public void whenValidate_OneFieldIsSizeViolation_ThenAddViolation() {
        Regrex regrex = Mockito.mock(Regrex.class);
        Mockito.when(regrex.pattern()).thenReturn("[a-zA-Z]*");
        Mockito.when(regrex.message()).thenReturn("First name is just use characters");
        regrexValidation.test(regrex, "abc12", violations, getField("firstName"));

        MockitoViolationsAssertion.create().expectField("firstName")
                .withMessage("First name is just use characters")
                .withInvalidValue("abc12").and().assertViolations(violations.iterator());
    }

    @Test
    public void whenValidate_NoFieldIsSizeViolation_ThenAddViolation() {
        Regrex regrex = Mockito.mock(Regrex.class);
        Mockito.when(regrex.pattern()).thenReturn("[a-zA-Z]*");
        Mockito.when(regrex.message()).thenReturn("First name is just use characters");
        regrexValidation.test(regrex, "abc", violations, getField("firstName"));

        Assert.assertEquals(0, violations.size());
    }
}
