package validator.mockito;

import annotation.NotNull;
import annotation.validation.NotNullValidation;
import annotation.validation.ValidationRule;
import annotation.validation.ValidationTypeDispatcher;
import model.Violation;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Not;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class MockitoValidationTypeDispatcherTest {

    private ValidationTypeDispatcher validationTypeDispatcher = new ValidationTypeDispatcher();

    private Collection<Violation> violations = new ArrayList<>();

    @Test(expected = UnsupportedOperationException.class)
    public void mapMethodWhen_ruleDoesNotSupport_thenThrowsUnsupportedOperationException() {
        Field field = Mockito.mock(Field.class);
        Ignore ignore = new Ignore() {

            @Override
            public String value() {
                return "a";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Ignore.class;
            }

        };

        validationTypeDispatcher.mapMethodTest(ignore, violations, field, null);
        Assert.assertEquals(15, violations.size());
    }

    @Test
    public void mapMethodWhen_ruleHasSupported_thenMapToMethodTest() {

        Field field = Mockito.mock(Field.class);
        NotNull notNull = new NotNull() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return NotNull.class;
            }

            @Override
            public String message() {
                return "First name must not be null";
            }
        };

        Mockito.when(field.getName()).thenReturn("firstName");

        validationTypeDispatcher.mapMethodTest(notNull, violations, field, null);
        MockitoViolationsAssertion.create().expectField("firstName")
                .withMessage("First name must not be null")
                .withInvalidValue(null)
                .and().assertViolations(violations.iterator());
    }


}
