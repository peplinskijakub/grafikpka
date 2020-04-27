package pl.grafikpka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grafikpka.model.Schedule;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {

    Optional<Schedule> findById(String id);

    @Query("{'date : ?0'}")
    List<Schedule>findByDate(String date);

}
