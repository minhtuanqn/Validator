package tuanle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * TODO Your implementation goes here
 */
public class DefaultValidator implements Validator{

    public void testNull(String value, NotNull notNull, Collection<Violation> collection, String fieldName) {
        if(value == null) {
            Collection<String> messages = new ArrayList<>();
            messages.add(notNull.message());
            Violation violation = new Violation();
            violation.setMessages(messages);
            violation.setInvalidValue(value);
            violation.setFieldName(fieldName);
            collection.add(violation);
        }
    }

    public void testRegex(String value, Regrex regrex, Collection<Violation> collection, String fieldName) {
        if (value == null || !value.matches(regrex.pattern())) {
            Collection<String> messages = new ArrayList<>();
            messages.add(regrex.message());
            Violation violation = new Violation();
            violation.setMessages(messages);
            violation.setFieldName(fieldName);
            violation.setInvalidValue(value);
            collection.add(violation);
        }
    }

    public void testSize(String value, Size size, Collection<Violation> collection, String fieldName) {
        if(value == null || value.length() > size.max() || value.length() < size.min()) {
            Collection<String> messages = new ArrayList<>();
            messages.add(size.message());
            Violation violation = new Violation();
            violation.setMessages(messages);
            violation.setFieldName(fieldName);
            violation.setInvalidValue(value);
            collection.add(violation);
        }
    }

    public void mapMethodTest(Annotation annotation, Collection<Violation> violations
            , Field field, Object value) {
        if(annotation.annotationType() == NotNull.class) {
            NotNull notNull = field.getAnnotation(NotNull.class);
            testNull((String) value, notNull, violations, field.getName());
        }
        if(annotation.annotationType() == Regrex.class) {
            Regrex regrex = field.getAnnotation(Regrex.class);
            testRegex((String) value, regrex, violations, field.getName());
        }
        if(annotation.annotationType() == Size.class) {
            Size size = field.getAnnotation(Size.class);
            testSize((String) value, size, violations, field.getName());
        }
    }

    public Collection<Violation> findAllViolation(Field [] fieldList, Object data) throws IllegalAccessException {
        Collection<Violation> violationCollection = new ArrayList<>();
        for (Field field : fieldList) {
            Object value = field.get(data);
            Annotation [] annotationFieldList = field.getAnnotations();
            for(int count = 0; count < annotationFieldList.length; count++) {
                Annotation annotation = annotationFieldList[count];
                mapMethodTest(annotation, violationCollection, field, value);
            }
        }
        return violationCollection;
    }

    /**
     * {@inheritDoc}
     *
     * @param data Input data
     * @return
     */
    @Override
    public Collection<Violation> validate(Object data) {
        if(data == null) {
            throw  new IllegalArgumentException();
        }
        Field fieldList[] = data.getClass().getDeclaredFields();
        try {
            Collection<Violation> violationCollection = findAllViolation(fieldList, data);
            return violationCollection;
        }
        catch (IllegalAccessException e) {
            Logger logger = Logger.getLogger("Logger");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        return null;
    }
}
