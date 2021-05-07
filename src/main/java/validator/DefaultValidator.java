package validator;

import annotation.validation.ValidationTypeDispatcher;
import model.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.text.MessageFormat;


/**
 * TODO Your implementation goes here
 */
public class DefaultValidator implements Validator {

    private Map<String, List<Annotation>> addedRuleFieldMap = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);
    private final ValidationTypeDispatcher dispatcher = new ValidationTypeDispatcher();


    /**
     * {@inheritDoc}
     *
     * @param data Input data
     * @return collection of violations
     */
    @Override
    public Collection<Violation> validate(Object data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        Collection<Violation> violationCollection = new ArrayList<>();
        Field[] fieldList = data.getClass().getDeclaredFields();

        for (Field field : fieldList) {
            try {
                field.setAccessible(true);
                Object value = field.get(data);
                Annotation[] annotationFieldList = field.getAnnotations();
                for (int count = 0; count < annotationFieldList.length; count++) {
                    Annotation annotation = annotationFieldList[count];
                    dispatcher.mapMethodTest(annotation, violationCollection, field, value);
                }
                field.setAccessible(false);
            }
            catch (IllegalAccessException e) {
                String message = MessageFormat.format("Error: Cannot access private field {0} of class {1}\n",
                        field.getName(), data.getClass().getCanonicalName());
                LOGGER.error(message, e.fillInStackTrace());
            }
            finally {
                field.setAccessible(false);
            }
        }
        addViolationFromAddedRule(data, violationCollection);
        return violationCollection;
    }

    /**
     * Add rule for field
     * @param fieldName
     * @param annotation
     */
    @Override
    public void addRuleForField(String fieldName, Annotation annotation) {
        if(addedRuleFieldMap == null) {
            addedRuleFieldMap = new HashMap<>();
        }
        List<Annotation> annotationList = new ArrayList<>();
        if(addedRuleFieldMap.containsKey(fieldName)) {
            annotationList = addedRuleFieldMap.get(fieldName);
            annotationList.add(annotation);
        }
        else {
            annotationList.add(annotation);
        }
        addedRuleFieldMap.put(fieldName, annotationList);
    }


    private void validateAddedViolation(Object data, Collection<Violation> violations)
            throws NoSuchFieldException {
        if(addedRuleFieldMap == null || addedRuleFieldMap.size() == 0) {
            return;
        }
        Iterator<String> iterator = addedRuleFieldMap.keySet().iterator();
        while(iterator.hasNext()) {
            String fieldName = iterator.next();
            List<Annotation> annotationList = addedRuleFieldMap.get(fieldName);
            for (Annotation annotation: annotationList) {
                Field field = data.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                try {
                    dispatcher.mapMethodTest(annotation, violations, field, field.get(data));
                }
                catch (IllegalAccessException e) {
                    String message = MessageFormat.format("Error: Cannot access private" +
                                    " field {0} of class {1}\n",
                            field.getName(), data.getClass().getCanonicalName());
                    LOGGER.error(message, e.fillInStackTrace());
                }
                finally {
                    field.setAccessible(false);
                }
            }
        }
    }


    private void addViolationFromAddedRule(Object data, Collection<Violation> violations) {
        try {
            validateAddedViolation(data, violations);
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.fillInStackTrace());
        }
    }
}
