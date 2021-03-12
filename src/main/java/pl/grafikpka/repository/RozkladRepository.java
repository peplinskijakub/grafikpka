package pl.grafikpka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.grafikpka.model.RodzajRozkladu;
import pl.grafikpka.model.TypRozkladu;

public interface RozkladRepository extends MongoRepository<RodzajRozkladu,String> {

    String findAllByTypRozkladu(TypRozkladu typRozkladu, String startLine, String godz);
}
