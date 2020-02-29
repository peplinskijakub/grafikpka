package pl.grafikpka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.grafikpka.model.RodzajRozkladu;
import pl.grafikpka.model.Schedule;
import pl.grafikpka.model.TypRozkladu;
import pl.grafikpka.repository.RozkladRepository;
import pl.grafikpka.repository.ScheduleRepository;

import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleRepository scheduleRepository;
    private RozkladRepository rozkladRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, RozkladRepository rozkladRepository) {
        this.scheduleRepository = scheduleRepository;
        this.rozkladRepository = rozkladRepository;
    }

    public boolean saveDataFromCsv(MultipartFile file, String date, TypRozkladu typRozkladu) {
        List<Schedule> scheduleList = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(';')
                    .withRecordSeparator(";").withIgnoreEmptyLines());
            for (CSVRecord record : csvParser) {
                Schedule schedule = new Schedule();
                schedule.setDate(date);
                schedule.setTypRozkladu(typRozkladu);
                schedule.setNrSluzbowy(record.get(0).trim());
                schedule.setLinia(record.get(1).trim());
                schedule.setPoczatekPracy(record.get(2).trim());
                schedule.setKoniecPracy(record.get(3).trim());
                schedule.setMiejsceZmiany(findAllByTypRozkladu(typRozkladu.name(), schedule.getLinia(), schedule.getPoczatekPracy()));
                if (record.get(0).isEmpty())
                    continue;
                scheduleList.add(schedule);
            }
            scheduleRepository.saveAll(scheduleList);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    @Transactional
    public Schedule findById(String id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (!scheduleOptional.isPresent()){
            try {
                throw  new NotFound();
            } catch (NotFound notFound) {
                notFound.printStackTrace();
            }
        }
        return scheduleOptional.get();
    }

    @Override
    public Set<Schedule> findScheduleByNrSluzbowy(String nrSluzbowy) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getNrSluzbowy()
                .equals(nrSluzbowy)).collect(Collectors.toSet());
    }


   public String findAllByTypRozkladu(String typRozkladu, String startLine, String godz) {
        List<RodzajRozkladu> rozkladList = new ArrayList<>();
        rozkladRepository.findAll().forEach(rozkladList::add);
        return rozkladList.stream()
                .filter(r -> r.getTypRozkladu().equals(typRozkladu) &&
                        r.getLinia().equals(startLine)
                        && r.getGodzina().equals(godz))
                .map(RodzajRozkladu::getMiejsceZmiany).findAny().orElse(null);
    }

    @Override
    public Set<Schedule> findByDate(String date) {
        Set<Schedule> scheduleSet = new HashSet<>();
        scheduleRepository.findAll().stream()
                .map(schedule -> schedule.getDate().equals(date))
                .collect(Collectors.toSet());
        return  scheduleSet;
    }

    @Override
    public void deleteById(String idToDelete) {
        scheduleRepository.deleteById(idToDelete);
    }
}
