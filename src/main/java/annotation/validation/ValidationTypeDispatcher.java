package annotation.validation;

import annotation.NotNull;
import annotation.Regrex;
import annotation.Size;
import model.Violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ValidationTypeDispatcher {

    private Map<Class<? extends Annotation>, ValidationRule> getMapAnnotation() {
        Map<Class<? extends Annotation>, ValidationRule> annotationMap = new HashMap<>();
        annotationMap.put(NotNull.class, new NotNullValidation());
        annotationMap.put(Regrex.class, new RegrexValidation());
        annotationMap.put(Size.class, new SizeValidation());
        return annotationMap;
    }


    /**
     * Map type of annotation and find method for test violation
     * @param annotation
     * @param violations
     * @param field
     * @param value
     */
    public void mapMethodTest(Annotation annotation, Collection<Violation> violations
            , Field field, Object value) {
        Map<Class<? extends Annotation>, ValidationRule> annotationMap = getMapAnnotation();
        if(annotationMap.containsKey(annotation.annotationType())) {
            ValidationRule validationObject = annotationMap.get(annotation.annotationType());
            validationObject.test(annotation, value, violations, field);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }
}
