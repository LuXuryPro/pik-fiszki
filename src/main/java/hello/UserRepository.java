package hello;

import dto.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserRepository extends MongoRepository <UserInfo,String> {
    List<UserInfo> findByFirstName(String firstName);
    List<UserInfo> findByLastName (String lastName);
    UserInfo findByEmail(String eMail);
    UserInfo findByuserId(String id);
}
