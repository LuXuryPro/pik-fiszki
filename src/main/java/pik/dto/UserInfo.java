package pik.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode
@Data
public class UserInfo {
    @Id
    private String userId;
    private String email;
    private String firstName;
    private String lastName;

    public UserInfo() {}

    public UserInfo(String id,String firstName, String lastName, String email) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
