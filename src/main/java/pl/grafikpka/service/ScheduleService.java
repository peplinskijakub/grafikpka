package pl.grafikpka.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.grafikpka.model.Schedule;
import pl.grafikpka.model.TypRozkladu;

import java.util.List;
import java.util.Set;

public interface ScheduleService {

    @Transactional
    Schedule findById(String id);

    Set<Schedule> findScheduleByNrSluzbowy(String workNb);

    Set<Schedule> findByDate(String date);


    boolean saveDataFromCsv(MultipartFile file, String date, TypRozkladu typRozkladu);

    void deleteById(String id);

    List<Schedule> findAll();

    Schedule save(Schedule schedule);
}
