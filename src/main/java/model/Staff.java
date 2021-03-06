package model;


import annotation.NotNull;
import annotation.Regrex;
import annotation.Size;

/**
 * Staff model
 */
public class Staff {

    @NotNull(message = "First name must not be null")
    public String firstName;

    @Size(min = 1, max = 3, message = "Last name size must be between 1 and 3")
    @Regrex(pattern = "[a-zA-Z]*", message = "Last name must be alphabetic characters only")
    public String lastName;

    public Integer age;

    /**
     * First name of staff
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Last name of staff
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
