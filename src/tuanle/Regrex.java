package tuanle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Regex rule
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Regrex {
    /**
     * Violation message
     *
     * @return Message
     */
    String message();

    /**
     * Regular expression
     *
     * @return Expression
     */
    String pattern();
}
