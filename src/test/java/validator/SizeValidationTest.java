package validator;


import annotation.NotNull;
import annotation.Regrex;
import annotation.Size;
import annotation.validation.SizeValidation;
import model.Violation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Size Validator
 */
public class SizeValidationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);

    @Test
    public void whenValidate_AllFieldIsViolation_ThenReturnViolation() throws NoSuchFieldException {
        Collection<Violation> violations = new ArrayList<>();
        final Student student = new Student("se", "leminhtuan", "1234567890123g");
        final SizeValidation sizeValidation = new SizeValidation();
        sizeValidation.test(getIdSizeAnnotation(),student.getId(), violations, Student.class.getDeclaredField("id"));
        sizeValidation.test(getNameSizeAnnotation(), student.getName(), violations, Student.class.getDeclaredField("name"));
        sizeValidation.test(getPhoneSizeAnnotation(), student.getPhone(), violations, Student.class.getDeclaredField("phone"));

        Iterator<Violation> iterator = violations.iterator();
        ViolationsAssertion.create().expectField("id")
                .withMessage("Id must from 3-5").withInvalidValue(student.getId())
                .and().expectField("name")
                .withMessage("Max of name is 8 characters").withInvalidValue(student.getName())
                .and().expectField("phone")
                .withMessage("Max of phone is 13 characters").withInvalidValue(student.getPhone())
                .and().assertViolations(iterator);
    }

    @Test
    public void whenValidate_OneFieldIsViolation_ThenReturnViolation() throws NoSuchFieldException {
        Collection<Violation> violations = new ArrayList<>();
        final Student student = new Student("sesd", "leminhtuan", "123456789012");
        final SizeValidation sizeValidation = new SizeValidation();
        sizeValidation.test(getIdSizeAnnotation(), student.getId(), violations, Student.class.getDeclaredField("id"));
        sizeValidation.test(getNameSizeAnnotation(), student.getName(), violations, Student.class.getDeclaredField("name"));
        sizeValidation.test(getPhoneSizeAnnotation(), student.getPhone(), violations, Student.class.getDeclaredField("phone"));

        Iterator<Violation> iterator = violations.iterator();
        ViolationsAssertion.create().expectField("name")
                .withMessage("Max of name is 8 characters").withInvalidValue(student.getName())
                .and().assertViolations(iterator);
    }

    @Test
    public void whenValidate_NoFieldIsViolation_ThenReturnViolation() throws NoSuchFieldException {
        Collection<Violation> violations = new ArrayList<>();
        final Student student = new Student("sesd", "tuan", "123456789012");
        final SizeValidation sizeValidation = new SizeValidation();
        sizeValidation.test(getIdSizeAnnotation(), student.getId(), violations, Student.class.getDeclaredField("id"));
        sizeValidation.test(getNameSizeAnnotation(), student.getName(), violations, Student.class.getDeclaredField("name"));
        sizeValidation.test(getPhoneSizeAnnotation(), student.getPhone(), violations, Student.class.getDeclaredField("phone"));

        Iterator<Violation> iterator = violations.iterator();
        ViolationsAssertion.create().assertViolations(iterator);
    }



    public static class Student {
        @NotNull(message = "ID can not be null")
        @Size(min = 3, max = 5,message = "Id must from 3-5")
        public String id;

        @Size(max = 8,message = "Max of name is 8 characters")
        public String name;

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

    public Size getIdSizeAnnotation() {
        try {
            Size idSize = Student.class.getDeclaredField("id").getDeclaredAnnotation(Size.class);
            return idSize;
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.getStackTrace());
        }
        return null;
    }

    public Size getNameSizeAnnotation() {
        try {
            Size nameSize = Student.class.getDeclaredField("name").getDeclaredAnnotation(Size.class);
            return nameSize;
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.getStackTrace());
        }
        return null;
    }



    public Size getPhoneSizeAnnotation() {
        try {
            Size phoneSize = Student.class.getDeclaredField("phone").getDeclaredAnnotation(Size.class);
            return phoneSize;
        }
        catch (NoSuchFieldException e) {
            String message = "Error: Cannot find field element by name\n";
            LOGGER.error(message, e.getStackTrace());
        }
        return null;
    }

}
