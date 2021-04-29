package annotation.validation;

import annotation.Regrex;
import model.Violation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class RegrexValidation {

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
     * Test regrex violation
     * @param value
     * @param regrex
     * @param collection
     * @param fieldName
     */
    public void testRegex(String value, Regrex regrex, Collection<Violation> collection, String fieldName) {
        if (value == null || !value.matches(regrex.pattern())) {
            Violation existViolation = checkExistViolation(collection, fieldName);
            if(existViolation == null) {
                Collection<String> messages = new ArrayList<>();
                messages.add(regrex.message());
                Violation violation = new Violation();
                violation.setMessages(messages);
                violation.setInvalidValue(value);
                violation.setFieldName(fieldName);
                collection.add(violation);
            }
            else {
                existViolation.getMessages().add(regrex.message());
            }
        }
    }
}
