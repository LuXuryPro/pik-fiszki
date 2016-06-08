package pik.repositories;

import pik.dto.MarkInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;


/**
 * Interfejs Dostępu do podstawowych operacji na kolekcji ocen
 */
public interface MarkRepository extends MongoRepository<MarkInfo,ObjectId> {
    /**
     * Find by course id list.
     *
     * @param courseId the course id
     * @return the list
     */
    List<MarkInfo> findByCourseId(BigInteger courseId);
}
