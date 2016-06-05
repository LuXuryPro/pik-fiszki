package pik.repositories;

import pik.dto.MarkInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Michał on 04.06.2016.
 */
public interface MarkRepository extends MongoRepository<MarkInfo,ObjectId> {
    List<MarkInfo> findByCourseId(BigInteger courseId);
}
