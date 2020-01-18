package pl.grafikpka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.grafikpka.model.RodzajRozkladu;

public interface RozkladRepository extends MongoRepository<RodzajRozkladu,String> {
}
