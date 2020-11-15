package pl.grafikpka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.grafikpka.model.RodzajRozkladu;
import pl.grafikpka.model.Schedule;
import pl.grafikpka.model.TypRozkladu;
import pl.grafikpka.repository.RozkladRepository;
import pl.grafikpka.repository.ScheduleRepository;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleRepository scheduleRepository;
    private RozkladRepository rozkladRepository;
    private final MongoTemplate mongoTemplate;
    private Schedule schedule;


    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, RozkladRepository rozkladRepository, MongoTemplate mongoTemplate) {
        this.scheduleRepository = scheduleRepository;
        this.rozkladRepository = rozkladRepository;
        this.mongoTemplate = mongoTemplate;
    }

    //    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, RozkladRepository rozkladRepository) throws ParseException {
//        this.scheduleRepository = scheduleRepository;
//        this.rozkladRepository = rozkladRepository;
//    }
    private static String INNE="INNE";
    private static String WOLNE="WOLNE";
    private static String CHORY="CHORY";
    private static String REZERWA="REZERWA";

    private static LocalDate datePerser = LocalDate.parse("2020-10-03");

//    private String convertStringToTime(String trim) {
//         LocalTime timePerser = LocalTime.parse("HH:mm");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//         return timePerser.format(formatter);
//    }

    public boolean saveDataFromCsv(MultipartFile file, LocalDate date, TypRozkladu typRozkladu) {
        List<Schedule> scheduleList = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(';')
                    .withRecordSeparator(";").withIgnoreEmptyLines());
            List<RodzajRozkladu> rozkladList = rozkladRepository.findAll();
            for (CSVRecord record : csvParser) {
                Schedule schedule = new Schedule();
                schedule.setDate(date);
                schedule.setTypRozkladu(typRozkladu);
                schedule.setUsername(record.get(0).trim());
                schedule.setLinia(record.get(1).trim());
                schedule.setPoczatekPracy(record.get(2).trim());
                schedule.setKoniecPracy(record.get(3).trim());
                schedule.setMiejsceZmiany(findAllByTypRozkladu(rozkladList, schedule.getTypRozkladu(),
                        schedule.getLinia(), String.valueOf(schedule.getPoczatekPracy())));
                if (record.get(0).isEmpty())
                    continue;
                scheduleList.add(schedule);
            }
            scheduleRepository.saveAll(scheduleList);
            csvParser.close();
            reader.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Schedule getById(String id) {
        return scheduleRepository.findById(id).orElse(null);

    }

    @Override
    public Set<Schedule> findSchedulesByUsername(String username) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getUsername()
                        .equals(username)).collect(Collectors.toSet());
    }


    public String findAllByTypRozkladu(List<RodzajRozkladu> rozkladList, TypRozkladu typRozkladu, String startLine, String godz) {
        return rozkladList.stream()
                .filter(r -> r.getTypRozkladu().equals(typRozkladu) &&
                        r.getLinia().equals(startLine)
                        && r.getGodzina().equals(godz))
                .map(RodzajRozkladu::getMiejsceZmiany).findAny().orElse("");
    }

    @Override
    public Set<String> findSchedulesByDate(String username) {
        Query query = new Query()
                .addCriteria(Criteria.where("username").is(username))
                .with(Sort.by(Sort.Order.asc("date")));
        List<Schedule> scheduleList = mongoTemplate.find(query, Schedule.class); //Lista z Query
      Set<String> listToSet = new TreeSet<>();
      for (Schedule date :scheduleList){
         String localDate= date.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          listToSet.add(localDate);
      }
        return listToSet;
    }
    @Override
    public List<Schedule> findSchedulesByUsernameAndDate(String username, String date) {
        Query query = new Query()
                .addCriteria(Criteria.where("username").is(username))
                .addCriteria(Criteria.where("date").is(datePerser.parse(date)))
                .with(Sort.by(Sort.Direction.ASC,"date"))
                .with(Sort.by(Sort.Direction.ASC,"poczatekPracy"))
                .limit(10);
        return mongoTemplate.find(query,Schedule.class);
    }
    @Override
    public void deleteById(String idToDelete) {
        scheduleRepository.deleteById(idToDelete);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}
