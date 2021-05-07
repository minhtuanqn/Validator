package annotation.validation;

import annotation.NotNull;
import model.Violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class NotNullValidation extends ValidationSupporter implements ValidationRule{

    /**
     * Test null violation
     * @param value
     * @param collection
     */
    @Override
    public void test(Annotation annotation, Object value, Collection<Violation> collection, Field field) {
        NotNull notNull = (NotNull) annotation;
        if(value == null) {
            Violation existViolation = checkExistViolation(collection, field.getName());
            if(existViolation == null) {

                Collection<String> messages = new ArrayList<>();
                messages.add(notNull.message());
                Violation violation = new Violation(value, messages, field.getName());
                collection.add(violation);
            }
            else {
                existViolation.getMessages().add(notNull.message());
            }
        }
    }

}
