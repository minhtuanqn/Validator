package validator;

import annotation.NotNull;
import annotation.Regrex;
import annotation.Size;
import annotation.validation.NotNullValidation;
import annotation.validation.RegrexValidation;
import model.Violation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class RegrexValidationTest {
    @Test
    public void whenValidate_TwoFieldIsViolation_ThenReturnViolation() throws NoSuchFieldException {
        Collection<Violation> violations = new ArrayList<>();
        final Student student = new Student("45fgfg",null, "098776577gddf");
        final RegrexValidation regrexValidation = new RegrexValidation();
        regrexValidation.test(student.getId(), violations, Student.class.getDeclaredField("id"));
        regrexValidation.test(student.getPhone(), violations, Student.class.getDeclaredField("phone"));

        Iterator<Violation> iterator = violations.iterator();
        ViolationsAssertion.create().expectField("id")
                .withMessage("ID is just used characters").withInvalidValue(student.getId())
                .and().expectField("phone")
                .withMessage("Phone number is just used digits").withInvalidValue(student.getPhone())
                .and().assertViolations(iterator);
    }



    public static class Student {

        @Regrex(pattern = "[a-zA-Z]*", message = "ID is just used characters")
        public String id;

        @NotNull(message = "Name can not be null")
        public String name;

        @NotNull(message = "Phone can not be null")
        @Size(max = 13, message = "Max of phone is 13 characters")
        @Regrex(pattern = "[0-9]{1,}", message = "Phone number is just used digits")
        public String phone;

        public Student() {
        }

        public Student(String id, String name, String phone) {
            this.id = id;
            this.name = name;
            this.phone = phone;
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
}
