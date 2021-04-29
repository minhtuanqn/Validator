package annotation.validation;

import annotation.NotNull;
import model.Violation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class NotNullValidation {

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
     * Test null violation
     * @param value
     * @param notNull
     * @param collection
     * @param fieldName
     */
    public void testNull(Object value, NotNull notNull, Collection<Violation> collection, String fieldName) {
        if(value == null) {
            Violation existViolation = checkExistViolation(collection, fieldName);
            if(existViolation == null) {
                Collection<String> messages = new ArrayList<>();
                messages.add(notNull.message());
                Violation violation = new Violation();
                violation.setMessages(messages);
                violation.setInvalidValue(value);
                violation.setFieldName(fieldName);
                collection.add(violation);
            }
            else {
                existViolation.getMessages().add(notNull.message());
            }
        }
    }

}
