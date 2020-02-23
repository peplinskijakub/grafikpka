package pl.grafikpka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.grafikpka.model.Schedule;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {

    @Override
    Optional<Schedule> findById(String id);
}
