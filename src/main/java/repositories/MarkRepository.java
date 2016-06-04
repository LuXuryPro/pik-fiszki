package repositories;

import dto.MarkInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Michał on 04.06.2016.
 */
public interface MarkRepository extends MongoRepository<MarkInfo,ObjectId> {
}
