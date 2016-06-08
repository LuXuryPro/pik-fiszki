package pik.repositories;

import org.springframework.data.mongodb.repository.Query;
import pik.dto.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Interfejs Dostępu do podstawowych operacji na kolekcji uzytkownikow
 */
public interface UserRepository extends MongoRepository <UserInfo,String> {
    /**
     * Find by first name list.
     *
     * @param firstName the first name
     * @return the list
     */
    List<UserInfo> findByFirstName(String firstName);

    /**
     * Find by last name list.
     *
     * @param lastName the last name
     * @return the list
     */
    List<UserInfo> findByLastName (String lastName);

    /**
     * Find by email user info.
     *
     * @param eMail the e mail
     * @return the user info
     */
    UserInfo findByEmail(String eMail);

    /**
     * Find by user id user info.
     *
     * @param id the id
     * @return the user info
     */
    UserInfo findByUserId(String id);

    /*@Query(value = "{ 'subscribedcourses' : {$all : [?0] }}")
    List<UserInfo> findBySubsbribedCourse(BigInteger[] course);*/
}
