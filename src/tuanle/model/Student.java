package tuanle.model;

import tuanle.NotNull;
import tuanle.Regrex;
import tuanle.Size;

public class Student {
    @Size(min = 1, max = 3, message = "ID size must be between 1 and 3")
    @Regrex(pattern = "[a-zA-Z]*", message = "ID must be alphabetic characters only")
    public String id;

    @NotNull(message = "Full name must not be null")
    public String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
