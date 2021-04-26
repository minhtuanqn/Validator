package tuanle.testing;

import tuanle.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class TestUtils {

    /**
     * Method for creating tested staff
     * @param firstName
     * @param lastName
     * @return
     */
    public static Staff createStaff(String firstName, String lastName) {
        Staff staff = new Staff();
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        return staff;
    }

    /**
     * Method for testing field name is null or not
     * @param fieldName
     * @param testedObject
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     */
    public static Violation testNullViolation(String fieldName, Object testedObject)
            throws ClassNotFoundException, NoSuchFieldException {
        Field field = Staff.class.getClassLoader().
                loadClass("tuanle.Staff").getDeclaredField(fieldName);
        NotNull notNull = field.getAnnotation(NotNull.class);
        if(testedObject == null) {
            Collection<String> messages = new ArrayList<>();
            messages.add(notNull.message());

            Violation nullViolation = new Violation();
            nullViolation.setInvalidValue((String) testedObject);
            nullViolation.setFieldName(fieldName);
            nullViolation.setMessages(messages);
            return nullViolation;
        }
        return null;
    }

    /**
     * Method for testing size violation
     * @param fieldName
     * @param testedString
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     */
    public static Violation testSizeViolation(String fieldName, String testedString)
            throws ClassNotFoundException, NoSuchFieldException {
        Field field = Staff.class.getClassLoader().
                loadClass("tuanle.Staff").getDeclaredField(fieldName);
        Size size = field.getAnnotation(Size.class);
        if(testedString == null || testedString.length() > size.max() || testedString.length() < size.min()) {
            Collection<String> messages = new ArrayList<>();
            messages.add(size.message());

            Violation sizeViolation = new Violation();
            sizeViolation.setInvalidValue((String) testedString);
            sizeViolation.setFieldName(fieldName);
            sizeViolation.setMessages(messages);
            return sizeViolation;
        }
        return null;
    }

    /**
     * Method for testing regrex violation
     * @param fieldName
     * @param testedString
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     */
    public static Violation testRegrexViolation(String fieldName, String testedString)
            throws ClassNotFoundException, NoSuchFieldException {
        Field field = Staff.class.getClassLoader().
                loadClass("tuanle.Staff").getDeclaredField(fieldName);
        Regrex regrex = field.getAnnotation(Regrex.class);
        if(testedString == null || !testedString.matches(regrex.pattern())) {
            Collection<String> messages = new ArrayList<>();
            messages.add(regrex.message());

            Violation regrexViolation = new Violation();
            regrexViolation.setInvalidValue((String) testedString);
            regrexViolation.setFieldName(fieldName);
            regrexViolation.setMessages(messages);
            return regrexViolation;
        }
        return null;

    }


    /**
     * Get collection of violation
     * @param staff
     * @return
     */
    public static Collection<Violation> createCollectionOfViolation(Staff staff) {
        Collection<Violation> violationList = new ArrayList<>();
        try {
            Violation nullFirstNameViolation = testNullViolation("firstName", staff.getFirstName());
            Violation regexLastnameViolation = testRegrexViolation("lastName", staff.getLastName());
            Violation sizeLastNameViolation = testSizeViolation("lastName", staff.getLastName());
            if(nullFirstNameViolation != null) {
                violationList.add(nullFirstNameViolation);
            }
            if(regexLastnameViolation != null) {
                violationList.add(regexLastnameViolation);
            }
            if(sizeLastNameViolation != null) {
                violationList.add(sizeLastNameViolation);
            }

            return violationList;
        }
        catch (ClassNotFoundException e) {
            Logger logger = Logger.getLogger("Test");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        catch (NoSuchFieldException e) {
            Logger logger = Logger.getLogger("Test");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        return null;
    }


}
