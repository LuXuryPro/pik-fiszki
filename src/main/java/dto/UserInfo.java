package dto;

import lombok.*;
import org.springframework.data.annotation.Id;

/*
    Sposób użycia:
    UserInfo.builder().email(...).firstName(...).lastName(...).build();
*/

@ToString
public class UserInfo {
    @Id
    private String email;
    private String firstName;
    private String lastName;

    public UserInfo() {
    }

    public UserInfo(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
