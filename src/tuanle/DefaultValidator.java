package tuanle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * TODO Your implementation goes here
 */
public class DefaultValidator implements Validator{

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

    /**
     * Test size violation
     * @param value
     * @param size
     * @param collection
     * @param fieldName
     */
    public void testSize(Object value, Size size, Collection<Violation> collection, String fieldName) {
        int realValue = 0;
        if(value.getClass() == String.class) {
            realValue = ((String) value).length();
        }
        if(value.getClass() == Integer.class) {
            realValue = (Integer) value;
        }
        int min = 1;
        if(size.min() > 0) {
            min = size.min();
        }
        if(value == null || realValue > size.max() || realValue < min) {
            Violation existViolation = checkExistViolation(collection, fieldName);
            if(existViolation == null) {
                Collection<String> messages = new ArrayList<>();
                messages.add(size.message());
                Violation violation = new Violation();
                violation.setMessages(messages);
                violation.setInvalidValue(value);
                violation.setFieldName(fieldName);
                collection.add(violation);
            }
            else {
                existViolation.getMessages().add(size.message());
            }
        }
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
        if(annotation.annotationType() == NotNull.class) {
            NotNull notNull = field.getAnnotation(NotNull.class);
            testNull(value, notNull, violations, field.getName());
        }
        if(annotation.annotationType() == Regrex.class) {
            Regrex regrex = field.getAnnotation(Regrex.class);
            testRegex((String) value, regrex, violations, field.getName());
        }
        if(annotation.annotationType() == Size.class) {
            Size size = field.getAnnotation(Size.class);
            testSize(value, size, violations, field.getName());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param data Input data
     * @return collection of violations
     */
    @Override
    public Collection<Violation> validate(Object data) {
        if(data == null) {
            throw  new IllegalArgumentException();
        }
        Collection<Violation> violationCollection = new ArrayList<>();
        Field fieldList[] = data.getClass().getDeclaredFields();
        try {
            for (Field field : fieldList) {
                field.setAccessible(true);
                Object value = field.get(data);
                Annotation [] annotationFieldList = field.getAnnotations();
                for(int count = 0; count < annotationFieldList.length; count++) {
                    Annotation annotation = annotationFieldList[count];
                    mapMethodTest(annotation, violationCollection, field, value);
                }
            }
            return violationCollection;
        }
        catch (IllegalAccessException e) {
            Logger logger = Logger.getLogger("Logger");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        return violationCollection;
    }
}
