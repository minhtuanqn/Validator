package annotation.validation;

import model.Violation;

import java.util.Collection;
import java.util.Iterator;

abstract class ValidationUtils {

    /**
     * Check exist violation of field
     * @param collection
     * @param fieldName
     * @return
     */
    Violation checkExistViolation(Collection<Violation> collection, String fieldName) {
        Iterator itr = collection.iterator();
        while (itr.hasNext()) {
            Violation violation = (Violation) itr.next();
            if(fieldName.equals(violation.getFieldName())) {
                return violation;
            }
        }
        return null;
    }
}
