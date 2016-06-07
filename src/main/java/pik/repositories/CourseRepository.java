package pik.repositories;

import org.springframework.data.mongodb.repository.Query;
import pik.dto.CourseInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Michał on 03.06.2016.
 */

public interface CourseRepository extends MongoRepository<CourseInfo,BigInteger> {
    CourseInfo findById(BigInteger id);
    CourseInfo findByName(String name);
    List<CourseInfo> findByOwnerId(String ownerId);
    CourseInfo findByOwnerIdAndName(String ownerId, String name);

    @Query("{ 'id' :  {$nin: ?0} }")
    List<CourseInfo> findByIdNotIn(Iterable<BigInteger> ids);
}
