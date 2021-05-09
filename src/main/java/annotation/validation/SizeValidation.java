package annotation.validation;

import annotation.Size;
import model.Violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class SizeValidation extends ValidationSupporter implements ValidationRule{

    /**
     * Test size violation
     * @param value
     * @param collection
     */
    @Override
    public void test(Annotation annotation, Object value, Collection<Violation> collection, Field field) {
        Size size = (Size) annotation;
        int realValue = 0;
        if(value == null) {

        }
        else if(value.getClass() == String.class) {
            realValue = ((String) value).length();
        }
        else if(value.getClass() == Integer.class) {
            realValue = (Integer) value;
        }
        if(realValue < 0) {
            throw new IllegalArgumentException();
        }
        int min = 1;
        if(size.min() > 0) {
            min = size.min();
        }
        if(min > size.max()) {
            throw new UnsupportedOperationException();
        }
        if(realValue > size.max() || realValue < min) {
            Violation existViolation = checkExistViolation(collection, field.getName());
            if(existViolation == null) {
                Collection<String> messages = new ArrayList<>();
                messages.add(size.message());
                Violation violation = new Violation(value, messages, field.getName());
                collection.add(violation);
            }
            else {
                existViolation.getMessages().add(size.message());
            }
        }
    }
}
