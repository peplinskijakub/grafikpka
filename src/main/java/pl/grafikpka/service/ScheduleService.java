package pl.grafikpka.service;

import org.springframework.web.multipart.MultipartFile;
import pl.grafikpka.model.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findAll();

    List<Schedule> findByDate(String date);


    boolean saveDataFromCsv(MultipartFile file, String date, String rozklad);
}
