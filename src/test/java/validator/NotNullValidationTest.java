package validator;

import annotation.NotNull;
import annotation.Regrex;
import annotation.Size;
import annotation.validation.NotNullValidation;
import model.Violation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class NotNullValidationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);

    @Test
    public void whenValidate_TwoFieldIsViolation_ThenReturnViolation() throws NoSuchFieldException {
        Collection<Violation> violations = new ArrayList<>();
        final SizeValidationTest.Student student = new SizeValidationTest.Student();
        final NotNullValidation notNullValidation = new NotNullValidation();
        notNullValidation.test(getNameNotNullAnnotation(), student.getName(), violations,
                NotNullValidationTest.Student.class.getDeclaredField("name"));
        notNullValidation.test(getPhoneNotNullAnnotation(), student.getPhone(), violations,
                NotNullValidationTest.Student.class.getDeclaredField("phone"));

        Iterator<Violation> iterator = violations.iterator();
        ViolationsAssertion.create().expectField("name")
                .withMessage("Name can not be null").withInvalidValue(student.getName())
                .and().expectField("phone")
                .withMessage("Phone can not be null").withInvalidValue(student.getPhone())
                .and().assertViolations(iterator);
    }

    @Test
    public void whenValidate_NoFieldIsViolation_ThenReturnViolation() throws NoSuchFieldException {
        Collection<Violation> violations = new ArrayList<>();
        final SizeValidationTest.Student student = new SizeValidationTest.Student();
        final NotNullValidation notNullValidation = new NotNullValidation();
        notNullValidation.test(getNameNotNullAnnotation(), student.getName(), violations,
                NotNullValidationTest.Student.class.getDeclaredField("name"));
        notNullValidation.test(getPhoneNotNullAnnotation(), student.getPhone(), violations, NotNullValidationTest.Student.class.getDeclaredField("phone"));

        Iterator<Violation> iterator = violations.iterator();
        ViolationsAssertion.create().assertViolations(iterator);
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
            NotNull idNotNull = Student.class.getDeclaredField("id").getDeclaredAnnotation(NotNull.class);
            return idNotNull;
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.getStackTrace());
        }
        return null;
    }

    public NotNull getNameNotNullAnnotation() {
        try {
            NotNull nameNotNull = Student.class.getDeclaredField("name").getDeclaredAnnotation(NotNull.class);
            return nameNotNull;
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.getStackTrace());
        }
        return null;
    }



    public NotNull getPhoneNotNullAnnotation() {
        try {
            NotNull phoneNotNull = Student.class.getDeclaredField("phone").
                    getDeclaredAnnotation(NotNull.class);
            return phoneNotNull;
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.getStackTrace());
        }
        return null;
    }

}
