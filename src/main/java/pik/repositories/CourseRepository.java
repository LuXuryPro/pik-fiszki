package pik.repositories;

import org.springframework.data.mongodb.repository.Query;
import pik.dto.CourseInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Interfejs Dostępu do podstawowych operacji na kolekcji kursow
 */
public interface CourseRepository extends MongoRepository<CourseInfo,BigInteger> {
    /**
     * Find by id course info.
     *
     * @param id the id
     * @return the course info
     */
    CourseInfo findById(BigInteger id);

    /**
     * Find by name course info.
     *
     * @param name the name
     * @return the course info
     */
    CourseInfo findByName(String name);

    /**
     * Find by owner id list.
     *
     * @param ownerId the owner id
     * @return the list
     */
    List<CourseInfo> findByOwnerId(String ownerId);

    /**
     * Find by owner id and name course info.
     *
     * @param ownerId the owner id
     * @param name    the name
     * @return the course info
     */
    CourseInfo findByOwnerIdAndName(String ownerId, String name);

    /**
     * Find by id not in list.
     *
     * @param ids the ids
     * @return the list
     */
    @Query("{ 'id' :  {$nin: ?0} }")
    List<CourseInfo> findByIdNotIn(Iterable<BigInteger> ids);
}
