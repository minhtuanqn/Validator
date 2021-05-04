package validator;

import annotation.validation.ValidationTypeDispatcher;
import model.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.text.MessageFormat;


/**
 * TODO Your implementation goes here
 */
public class DefaultValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);

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
                    ValidationTypeDispatcher dispatcher = new ValidationTypeDispatcher();
                    dispatcher.mapMethodTest(annotation, violationCollection, field, value);
                }
                field.setAccessible(false);
            }
            catch (IllegalAccessException e) {
                field.setAccessible(false);
                String message = MessageFormat.format("Error: Cannot access private field {0} of class {1}\n",
                        field.getName(), data.getClass().getCanonicalName());
                LOGGER.error(message, e.getMessage());
            }
        }
        return violationCollection;
    }
}
