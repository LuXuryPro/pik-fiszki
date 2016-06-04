package pik.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
public class UserInfo {
    @Id
    private String userId;
    private String email;
    private String firstName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String lastName;

    public UserInfo() {}

    public UserInfo(String id,String firstName, String lastName, String email) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
