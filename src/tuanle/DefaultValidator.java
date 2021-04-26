package tuanle;

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

    private Field firstnameField;
    private Field lastnameField;

    private void testNull(String value, NotNull notNull, Collection<Violation> collection, String fieldName) {
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

    private void testRegex(String value, Regrex regrex, Collection<Violation> collection, String fieldName) {
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

    private void testSize(String value, Size size, Collection<Violation> collection, String fieldName) {
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
        Collection<Violation> collection = new ArrayList<>();
        Staff staff = (Staff) data;
        String firstname = staff.getFirstName();
        String lastname = staff.getLastName();

        try {
            firstnameField = Staff.class.getClassLoader().
                    loadClass("tuanle.Staff").getDeclaredField("firstName");
            lastnameField = Staff.class.getClassLoader().
                    loadClass("tuanle.Staff").getDeclaredField("lastName");
            NotNull notNull = firstnameField.getAnnotation(NotNull.class);
            Regrex regrex = lastnameField.getAnnotation(Regrex.class);
            Size size = lastnameField.getAnnotation(Size.class);

            testNull(firstname, notNull, collection, "firstName");
            testRegex(lastname, regrex, collection, "lastName");
            testSize(lastname, size, collection, "lastName");
        }
        catch (ClassNotFoundException e) {
            Logger logger = Logger.getLogger("Test");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        catch (NoSuchFieldException e) {
            Logger logger = Logger.getLogger("Test");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        return collection;
    }
}
