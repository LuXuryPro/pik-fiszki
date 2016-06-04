package pik.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfo {

    @Id
    @Indexed
    private String userId;

    private String email;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String userName;

    private  List<BigInteger> subscribedcourses = new ArrayList<>();

    @DBRef(db="mark")
    private List<MarkInfo> marks = new ArrayList<>();


    public UserInfo() {}

    public UserInfo(String id,String firstName, String lastName, String email, String userName) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<BigInteger> getSubscribedCourses() {
        return subscribedcourses;
    }

    public void setSubscribedCourses(List<BigInteger> subscribedcourses) {
        this.subscribedcourses = subscribedcourses;
    }

    public List<MarkInfo> getMarks() {
        return marks;
    }

    public void setMarks(List<MarkInfo> marks) {
        this.marks = marks;
    }
}
