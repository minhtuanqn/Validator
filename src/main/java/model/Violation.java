package model;

import java.util.Collection;

/**
 * Supply information about constraint violation
 */
public class Violation {

    private Object invalidValue;
    private Collection<String> messages;
    private String fieldName;

    /**
     *
     * @return Invalid value
     */
    public Object getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(Object invalidValue) {
        this.invalidValue = invalidValue;
    }

    /**
     *
     * @return List of message for invalid value
     */
    public Collection<String> getMessages() {
        return messages;
    }

    public void setMessages(Collection<String> messages) {
        this.messages = messages;
    }

    /**
     *
     * @return Name of field of invalid value
     */
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "fieldName: '" + getFieldName() + "', " +
                "invalidValue: '" + getInvalidValue() + "', " +
                "messages: '" + getMessages() + "'" +
                "}";
    }
}
