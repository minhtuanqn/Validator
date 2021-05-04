package annotation.validation;

import annotation.Regrex;
import model.Violation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class RegrexValidation extends ValidationUtils implements ValidationRule{

    /**
     * Test regrex violation
     * @param value
     * @param collection
     */
    @Override
    public void test(Object value, Collection<Violation> collection, Field field) {
        Regrex regrex = field.getAnnotation(Regrex.class);
        if (value == null || !((String) value).matches(regrex.pattern())) {
            Violation existViolation = checkExistViolation(collection, field.getName());
            if(existViolation == null) {
                Collection<String> messages = new ArrayList<>();
                messages.add(regrex.message());
                Violation violation = new Violation();
                violation.setMessages(messages);
                violation.setInvalidValue(value);
                violation.setFieldName(field.getName());
                collection.add(violation);
            }
            else {
                existViolation.getMessages().add(regrex.message());
            }
        }
    }
}
