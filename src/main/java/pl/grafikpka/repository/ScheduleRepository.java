package pl.grafikpka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.grafikpka.model.Schedule;
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {
}
