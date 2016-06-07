package pik.JSONDao;

public class EditProfileJsonRequest {
    public String firstName;

    public EditProfileJsonRequest() {
    }

    public EditProfileJsonRequest(String firstName, String secondName, String email) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
    }

    public String secondName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String email;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String Id;
}
