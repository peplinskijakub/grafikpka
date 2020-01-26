package pl.grafikpka.service;

import org.springframework.web.multipart.MultipartFile;
import pl.grafikpka.model.Schedule;

import java.util.List;
import java.util.Set;

public interface ScheduleService {
    List<Schedule> findAll();

    Set<Schedule> findByDate(String date);


    boolean saveDataFromCsv(MultipartFile file, String date, String rozklad);

    void deleteById(String id);
}
