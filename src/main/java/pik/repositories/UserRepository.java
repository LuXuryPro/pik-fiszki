package pik.repositories;

import org.springframework.data.mongodb.repository.Query;
import pik.dto.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

public interface UserRepository extends MongoRepository <UserInfo,String> {
    List<UserInfo> findByFirstName(String firstName);
    List<UserInfo> findByLastName (String lastName);
    UserInfo findByEmail(String eMail);
    UserInfo findByUserId(String id);
    UserInfo findByUserName(String id);

    /*@Query(value = "{ 'subscribedcourses' : {$all : [?0] }}")
    List<UserInfo> findBySubsbribedCourse(BigInteger[] course);*/
}
