package annotation.validation;

import annotation.Size;
import model.Violation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SizeValidation {

    /**
     * Check exist violation of field
     * @param collection
     * @param fieldName
     * @return
     */
    public Violation checkExistViolation(Collection<Violation> collection, String fieldName) {
        Iterator itr = collection.iterator();
        while (itr.hasNext()) {
            Violation violation = (Violation) itr.next();
            if(fieldName.equals(violation.getFieldName())) {
                return violation;
            }
        }
        return null;
    }

    /**
     * Test size violation
     * @param value
     * @param size
     * @param collection
     * @param fieldName
     */
    public void testSize(Object value, Size size, Collection<Violation> collection, String fieldName) {
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
            Violation existViolation = checkExistViolation(collection, fieldName);
            if(existViolation == null) {
                Collection<String> messages = new ArrayList<>();
                messages.add(size.message());
                Violation violation = new Violation();
                violation.setMessages(messages);
                violation.setInvalidValue(value);
                violation.setFieldName(fieldName);
                collection.add(violation);
            }
            else {
                existViolation.getMessages().add(size.message());
            }
        }
    }
}
