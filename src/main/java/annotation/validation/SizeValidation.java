package annotation.validation;

import annotation.Size;
import model.Violation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class SizeValidation extends ValidationUtils implements ValidationRule{

    /**
     * Test size violation
     * @param value
     * @param collection
     */
    @Override
    public void test(Object value, Collection<Violation> collection, Field field) {
        Size size = field.getAnnotation(Size.class);
        int realValue = 0;
        if(value.getClass() == String.class) {
            realValue = ((String) value).length();
        }
        if(value.getClass() == Integer.class) {
            realValue = (Integer) value;
        }
        int min = 1;
        if(size.min() > 0) {
            min = size.min();
        }
        if(value == null || realValue > size.max() || realValue < min) {
            Violation existViolation = checkExistViolation(collection, field.getName());
            if(existViolation == null) {
                Collection<String> messages = new ArrayList<>();
                messages.add(size.message());
                Violation violation = new Violation();
                violation.setMessages(messages);
                violation.setInvalidValue(value);
                violation.setFieldName(field.getName());
                collection.add(violation);
            }
            else {
                existViolation.getMessages().add(size.message());
            }
        }
    }
}
