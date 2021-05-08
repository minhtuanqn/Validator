package validator.mockito;

import annotation.NotNull;
import annotation.validation.NotNullValidation;
import model.Staff;
import model.Violation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Not;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import validator.DefaultValidator;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(MockitoJUnitRunner.class)
public class MockitoNotNullValidatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);

    private Collection<Violation> violations = new ArrayList<>();

    private Object data = null;

    private final NotNullValidation notNullValidation =  new NotNullValidation();

    private NotNull notNull = Mockito.mock(NotNull.class);

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
    public void whenValidate_OneFieldIsNullViolation_ThenReturnViolation() {

        Mockito.when(notNull.message()).thenReturn("First name must not be null");
        notNullValidation.test(notNull, data,
                violations, getField("firstName"));

        MockitoViolationsAssertion.create().expectField("firstName")
                .withMessage("First name must not be null")
                .withInvalidValue(null)
                .and().assertViolations(violations.iterator());

    }

}
