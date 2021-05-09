package validator.mockito;

import annotation.Size;
import annotation.validation.SizeValidation;
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
public class MockitoSizeValidationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);

    private Collection<Violation> violations = new ArrayList<>();

    private SizeValidation sizeValidation = new SizeValidation();

    @Test
    public void whenValidate_OneFieldIsSizeViolation_ThenAddViolation() {
        Field field = Mockito.mock(Field.class);
        Size size = Mockito.mock(Size.class);

        Mockito.when(field.getName()).thenReturn("firstName");
        Mockito.when(size.min()).thenReturn(5);
        Mockito.when(size.max()).thenReturn(13);
        Mockito.when(size.message()).thenReturn("First name is from 5-13 characters");
        sizeValidation.test(size, "12", violations, field);

        MockitoViolationsAssertion.create().expectField("firstName")
                .withMessage("First name is from 5-13 characters")
                .withInvalidValue("12").and().assertViolations(violations.iterator());
    }

    @Test
    public void whenValidate_NoFieldIsSizeViolation_ThenReturn() {
        Field field = Mockito.mock(Field.class);
        Size size = Mockito.mock(Size.class);

        Mockito.when(field.getName()).thenReturn("firstName");
        Mockito.when(size.max()).thenReturn(13);
        Mockito.when(size.message()).thenReturn("First name is from 1-13 characters");
        sizeValidation.test(size, "1", violations, field);

        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void whenValidate_OneIntegerFieldIsSizeViolation_ThenReturn() {
        Size size = Mockito.mock(Size.class);
        Field field = Mockito.mock(Field.class);

        Mockito.when(field.getName()).thenReturn("age");
        Mockito.when(size.min()).thenReturn(18);
        Mockito.when(size.max()).thenReturn(60);
        Mockito.when(size.message()).thenReturn("Age must be from 8-60");

        sizeValidation.test(size, 61, violations, field);
        MockitoViolationsAssertion.create().expectField("age")
                .withInvalidValue(61).withMessage("Age must be from 8-60")
                .and().assertViolations(violations.iterator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenValidate_ParametersAreInvalid_ThenThrowsIllegalArgumentException() {
        Size size = Mockito.mock(Size.class);
        Field field = Mockito.mock(Field.class);

        Mockito.when(field.getName()).thenReturn("age");
        Mockito.when(size.min()).thenReturn(2);
        Mockito.when(size.max()).thenReturn(18);
        Mockito.when(size.message()).thenReturn("Age must be from 8-60");

        sizeValidation.test(size, -4, violations, field);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenValidate_MinGreaterThanMaxInRule_ThenThrowsUnsupportedOperationException() {
        Size size = Mockito.mock(Size.class);
        Field field = Mockito.mock(Field.class);

        Mockito.when(field.getName()).thenReturn("age");
        Mockito.when(size.min()).thenReturn(3);
        Mockito.when(size.max()).thenReturn(2);
        Mockito.when(size.message()).thenReturn("Age must be from 8-60");

        sizeValidation.test(size, 6, violations, field);
    }

}
