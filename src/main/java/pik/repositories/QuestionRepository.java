package pik.repositories;


import org.springframework.data.domain.Sort;
import pik.dto.QuestionInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;


/**
 * Interfejs Dostępu do podstawowych operacji na kolekcji pytan
 */
public interface QuestionRepository extends MongoRepository<QuestionInfo, BigInteger> {
    /**
     * Find by id question info.
     *
     * @param courseId the course id
     * @return the question info
     */
    QuestionInfo findById(BigInteger courseId);

    /**
     * Find by course id list.
     *
     * @param courseId CourseId
     * @return Lista
     */
    List<QuestionInfo> findByCourseId(BigInteger courseId);

    /**
     * Count by course id
     *
     * @param id the id
     * @return count
     */
    Long countByCourseId(BigInteger id);
}
