package annotation.validation;

import annotation.NotNull;
import annotation.Regrex;
import annotation.Size;
import model.Violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

public class ValidationTypeDispatcher {
    /**
     * Map type of annotation and find method for test violation
     * @param annotation
     * @param violations
     * @param field
     * @param value
     */
    public void mapMethodTest(Annotation annotation, Collection<Violation> violations
            , Field field, Object value) {
        if(annotation.annotationType() == NotNull.class) {
            NotNull notNull = field.getAnnotation(NotNull.class);
            NotNullValidation notNullValidation = new NotNullValidation();
            notNullValidation.testNull(value, notNull, violations, field.getName());
        }
        if(annotation.annotationType() == Regrex.class) {
            Regrex regrex = field.getAnnotation(Regrex.class);
            RegrexValidation regrexValidation = new RegrexValidation();
            regrexValidation.testRegex((String) value, regrex, violations, field.getName());
        }
        if(annotation.annotationType() == Size.class) {
            Size size = field.getAnnotation(Size.class);
            SizeValidation sizeValidation = new SizeValidation();
            sizeValidation.testSize(value, size, violations, field.getName());
        }
    }
}
