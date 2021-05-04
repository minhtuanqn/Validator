package annotation.validation;

import annotation.NotNull;
import model.Violation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class NotNullValidation extends ValidationUtils implements ValidationRule{

    /**
     * Test null violation
     * @param value
     * @param collection
     */
    @Override
    public void test(Object value, Collection<Violation> collection, Field field) {
        NotNull notNull = field.getAnnotation(NotNull.class);
        if(value == null) {
            Violation existViolation = checkExistViolation(collection, field.getName());
            if(existViolation == null) {

                Collection<String> messages = new ArrayList<>();
                messages.add(notNull.message());
                Violation violation = new Violation();
                violation.setMessages(messages);
                violation.setInvalidValue(value);
                violation.setFieldName(field.getName());
                collection.add(violation);
            }
            else {
                existViolation.getMessages().add(notNull.message());
            }
        }
    }

}
