package validator;

import model.Violation;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Validate input data with annotation-based approach
 */
public interface Validator {
    /**
     * Validate data
     *
     * @param data Input data
     * @return Collection of violation instances
     */
    Collection<Violation> validate(Object data);

    void addRuleForField(String fieldName, Annotation annotation);
}
