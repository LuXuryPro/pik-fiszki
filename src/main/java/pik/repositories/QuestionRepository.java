package pik.repositories;


import org.springframework.data.domain.Sort;
import pik.dto.QuestionInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Michał on 03.06.2016.
 */


public interface QuestionRepository extends MongoRepository<QuestionInfo, BigInteger> {
    QuestionInfo findById(BigInteger courseId);
    List<QuestionInfo> findByCourseId(BigInteger courseId);
}
