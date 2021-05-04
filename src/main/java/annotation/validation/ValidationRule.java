package annotation.validation;

import model.Violation;

import java.lang.reflect.Field;
import java.util.Collection;

public interface ValidationRule {
    void test(Object value, Collection<Violation> collection, Field field);
}
