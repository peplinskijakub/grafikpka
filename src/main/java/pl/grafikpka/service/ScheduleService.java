package pl.grafikpka.service;

import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.grafikpka.model.Schedule;
import pl.grafikpka.model.TypRozkladu;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Profile("web")
public interface ScheduleService {

    Schedule save(Schedule schedule);

    @Transactional
    Schedule getById(String id);

    Set<Schedule> findSchedulesByUsername(String username);

    boolean saveDataFromCsv(MultipartFile file, LocalDate date, TypRozkladu typRozkladu);

    void deleteById(String id);

    List<Schedule> findAll();

    Set<String> findSchedulesByDate(String username);

    List<Schedule> findSchedulesByUsernameAndDate(String username, String date);
}
