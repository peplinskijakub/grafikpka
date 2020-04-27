package pl.grafikpka.service;

import org.springframework.transaction.annotation.Transactional;
import pl.grafikpka.model.RodzajRozkladu;
import pl.grafikpka.model.TypRozkladu;

import java.util.List;

public interface RozkladService{
    List<RodzajRozkladu> findAll();

    boolean saveRozklad(TypRozkladu typRozkladu, String linia, String brygada, String godzina,
                        String miejsceZmiany, String pierwszaLinia);

    @Transactional
    RodzajRozkladu getById(String id);

    void deleteById(String id);

    RodzajRozkladu save(RodzajRozkladu rodzajRozkladu);
}
