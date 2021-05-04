package annotation.validation;

import annotation.NotNull;
import annotation.Regrex;
import annotation.Size;
import model.Violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ValidationTypeDispatcher {

    private Map<Class, Object> getMapAnnotation() {
        Map<Class, Object> annotationMap = new HashMap<>();
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
        Map<Class, Object> annotationMap = getMapAnnotation();
        if(annotationMap.containsKey(annotation.annotationType())) {
            Object validationObject = annotationMap.get(annotation.annotationType());
            String className = validationObject.getClass().getSimpleName();
            String methodName = "test" + className.substring(0, className.length() - 10);
            try {
                Method method = validationObject.getClass().getMethod(methodName, Object.class,
                        Collection.class, Field.class);
                method.invoke(validationObject, value, violations, field);
            }
            catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                Logger logger = Logger.getLogger("MethodEx");
                logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
            }

        }


//        if(annotation.annotationType() == NotNull.class) {
//            NotNull notNull = field.getAnnotation(NotNull.class);
//            NotNullValidation notNullValidation = new NotNullValidation();
//            notNullValidation.testNull(value, notNull, violations, field.getName());
//        }
//        if(annotation.annotationType() == Regrex.class) {
//            Regrex regrex = field.getAnnotation(Regrex.class);
//            RegrexValidation regrexValidation = new RegrexValidation();
//            regrexValidation.testRegex((String) value, regrex, violations, field.getName());
//        }
//        if(annotation.annotationType() == Size.class) {
//            Size size = field.getAnnotation(Size.class);
//            SizeValidation sizeValidation = new SizeValidation();
//            sizeValidation.testSize(value, size, violations, field.getName());
//        }
    }
}