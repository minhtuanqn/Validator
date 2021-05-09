package validator.mockito;

import annotation.NotNull;
import annotation.validation.NotNullValidation;
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
public class MockitoNotNullValidatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);

    private Collection<Violation> violations = new ArrayList<>();

    private final NotNullValidation notNullValidation =  new NotNullValidation();


    @Test
    public void whenValidate_OneFieldIsNullViolation_ThenAddViolation() {
        NotNull notNull = Mockito.mock(NotNull.class);
        Field field = Mockito.mock(Field.class);
        Mockito.when(field.getName()).thenReturn("firstName");

        Mockito.when(notNull.message()).thenReturn("First name must not be null");
        notNullValidation.test(notNull, null,
                violations, field);

        MockitoViolationsAssertion.create().expectField("firstName")
                .withMessage("First name must not be null")
                .withInvalidValue(null)
                .and().assertViolations(violations.iterator());

    }

    @Test
    public void whenValidate_NoFieldIsValidation_ThenReturn() {
        NotNull notNull = Mockito.mock(NotNull.class);
        Field field = Mockito.mock(Field.class);

        Mockito.when(field.getName()).thenReturn("firstName");
        Mockito.when(notNull.message()).thenReturn("First name must not be null");
        notNullValidation.test(notNull, "", violations, field);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void whenValidate_OneNewFieldViolationAndAExistedViolationField_ThenAddToList() {
        NotNull notNull = Mockito.mock(NotNull.class);
        Field field = Mockito.mock(Field.class);

        Mockito.when(field.getName()).thenReturn("lastName");

        Collection<String> messages = new ArrayList<>();
        messages.add("Last name must be 1-5 characters");
        messages.add("Last name just use character");
        Violation violation = new Violation(null, messages, "lastName");
        violations.add(violation);


        Mockito.when(notNull.message()).thenReturn("Last name must not be null");
        notNullValidation.test(notNull, null, violations, field);

        MockitoViolationsAssertion.create().expectField("lastName")
                .withInvalidValue(null).withMessage("Last name must not be null")
                .withMessage("Last name must be 1-5 characters")
                .withMessage("Last name just use character")
                .and()
                .assertViolations(violations.iterator());

    }

}
