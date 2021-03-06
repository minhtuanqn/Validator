package validator.junit;

import annotation.NotNull;
import annotation.Regrex;
import annotation.Size;
import annotation.validation.NotNullValidation;
import model.Violation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import validator.DefaultValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class JUnitNotNullValidationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);

    @Test
    public void whenValidate_TwoFieldIsViolation_ThenReturnViolation() throws NoSuchFieldException {
        Collection<Violation> violations = new ArrayList<>();
        final JUnitSizeValidationTest.Student student = new JUnitSizeValidationTest.Student();
        final NotNullValidation notNullValidation = new NotNullValidation();
        notNullValidation.test(getNameNotNullAnnotation(), student.getName(), violations,
                JUnitNotNullValidationTest.Student.class.getDeclaredField("name"));
        notNullValidation.test(getPhoneNotNullAnnotation(), student.getPhone(), violations,
                JUnitNotNullValidationTest.Student.class.getDeclaredField("phone"));

        Iterator<Violation> iterator = violations.iterator();
        JUnitViolationsAssertion.create().expectField("name")
                .withMessage("Name can not be null").withInvalidValue(student.getName())
                .and().expectField("phone")
                .withMessage("Phone can not be null").withInvalidValue(student.getPhone())
                .and().assertViolations(iterator);
    }

    @Test
    public void whenValidate_NoFieldIsViolation_ThenReturnViolation() throws NoSuchFieldException {
        Collection<Violation> violations = new ArrayList<>();
        final JUnitSizeValidationTest.Student student = new JUnitSizeValidationTest.Student();
        final NotNullValidation notNullValidation = new NotNullValidation();
        notNullValidation.test(getNameNotNullAnnotation(), student.getName(), violations,
                JUnitNotNullValidationTest.Student.class.getDeclaredField("name"));
        notNullValidation.test(getPhoneNotNullAnnotation(), student.getPhone(), violations, JUnitNotNullValidationTest.Student.class.getDeclaredField("phone"));

        Iterator<Violation> iterator = violations.iterator();
        JUnitViolationsAssertion.create().assertViolations(iterator);
    }



    public static class Student {

        public String id;

        @NotNull(message = "Name can not be null")
        public String name;

        @NotNull(message = "Phone can not be null")
        @Size(max = 13, message = "Max of phone is 13 characters")
        @Regrex(pattern = "[0-9]{1,}", message = "Phone number is just used digits")
        public String phone;

        public Student() {}

        public Student(String id, String name, String phone) {
            this.id = id;
            this.name = name;
            this.phone= phone;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public NotNull getIdNotNullAnnotation() {
        try {
            return Student.class.getDeclaredField("id").getDeclaredAnnotation(NotNull.class);
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.fillInStackTrace());
        }
        return null;
    }

    public NotNull getNameNotNullAnnotation() {
        try {
            return Student.class.getDeclaredField("name").getDeclaredAnnotation(NotNull.class);
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.fillInStackTrace());
        }
        return null;
    }



    public NotNull getPhoneNotNullAnnotation() {
        try {
            return Student.class.getDeclaredField("phone").
                    getDeclaredAnnotation(NotNull.class);
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.fillInStackTrace());
        }
        return null;
    }

}
