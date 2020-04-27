package pl.grafikpka.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.grafikpka.model.Schedule;
import pl.grafikpka.model.TypRozkladu;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ScheduleService {

    @Transactional
    Schedule getById(String id);

    Set<Schedule> findSchedulesByUsename(String username);

    Set<Schedule> findByDate(String date);


    boolean saveDataFromCsv(MultipartFile file, LocalDate date, TypRozkladu typRozkladu);

    void deleteById(String id);

    List<Schedule> findAll();

    Schedule save(Schedule schedule);
}
