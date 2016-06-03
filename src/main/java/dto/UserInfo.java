package dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
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

    private String userName;

    private  List<BigInteger> subscribedcourses = new ArrayList<>();

    @DBRef(db="mark")
    private  List<MarkInfo> marks = new ArrayList<>();

    public UserInfo() {}

    public UserInfo(String id,String firstName, String lastName, String email, String userName) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
    }
}
