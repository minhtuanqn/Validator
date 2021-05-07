package annotation.validation;

import model.Violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

public interface ValidationRule {
    void test(Annotation annotation, Object value, Collection<Violation> collection, Field field);

}
