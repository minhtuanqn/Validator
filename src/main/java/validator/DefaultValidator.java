package validator;

import annotation.NotNull;
import annotation.Regrex;
import annotation.Size;
import annotation.validation.NotNullValidation;
import annotation.validation.RegrexValidation;
import annotation.validation.SizeValidation;
import annotation.validation.ValidationTypeDispatcher;
import model.Violation;

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
public class DefaultValidator implements Validator {

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
                    ValidationTypeDispatcher dispatcher = new ValidationTypeDispatcher();
                    dispatcher.mapMethodTest(annotation, violationCollection, field, value);
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
