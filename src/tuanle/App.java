package tuanle;

import tuanle.model.Student;

import java.util.Collection;
import java.util.Iterator;

public class App {
    public static void main(String[] args) {
        // Initialize validator
        final Validator validator = new DefaultValidator();

        // Validated data
        final Student student = new Student();
        student.setFullname(null);
        student.setId("123");

        // Validate and return violation. One violation is only responsible for
        // one field violation. If that field violates multiple constraints, it should
        // have multiple error messages. Please inspect Violation model for details
        final Collection<Violation> violations = validator.validate(student);

        if(violations != null) {
            Iterator<Violation> iterator = violations.iterator();
            while(iterator.hasNext()) {
                Violation violation = iterator.next();
                System.out.println(violation);
            }
        }
    }

}
