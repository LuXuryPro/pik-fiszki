package dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Data
@Document(collection = "user")
public class UserInfo {
    @Id
    private String userId;

    private String email;

    private String firstName;

    private String lastName;

    @DBRef(db="course")
    private  List<CourseInfo> courses = new ArrayList<>();

    public UserInfo() {}

    @PersistenceConstructor
    public UserInfo(String id,String firstName, String lastName, String email) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
