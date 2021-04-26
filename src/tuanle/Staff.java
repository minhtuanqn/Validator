package tuanle;

/**
 * Staff model
 */
public class Staff {

    @NotNull(message = "First name must not be null")
    private String firstName;

    @Size(min = 1, max = 3, message = "Last name size must be between 1 and 3")
    @Regrex(pattern = "[a-zA-Z]*", message = "Last name must be alphabetic characters only")
    private String lastName;

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

    @Override
    public String toString() {
        return "Staff{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
