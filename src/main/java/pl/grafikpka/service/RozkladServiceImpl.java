package pl.grafikpka.service;

import org.springframework.stereotype.Service;
import pl.grafikpka.model.RodzajRozkladu;
import pl.grafikpka.model.TypRozkladu;
import pl.grafikpka.repository.RozkladRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RozkladServiceImpl implements RozkladService {
    private RozkladRepository rozkladRepository;

    public RozkladServiceImpl(RozkladRepository rozkladRepository) {
        this.rozkladRepository = rozkladRepository;
    }

    String findAllByTypRozkladu(TypRozkladu typRozkladu, String startLine, String godz) {
        List<RodzajRozkladu> rozkladList = new ArrayList<>();
        rozkladRepository.findAll().forEach(rozkladList::add);
        return rozkladList.stream()
                .filter(r -> r.getTypRozkladu().equals(typRozkladu) &&
                        r.getLinia().equals(startLine)
                        && r.getGodzina().equals(godz))
                .map(RodzajRozkladu::getMiejsceZmiany).findAny().orElse("");

    }

    @Override
    public boolean saveRozklad(TypRozkladu typRozkladu, String linia, String brygada, String godzina,
                               String miejsceZmiany, String pierwszaLinia) {
        try {
            RodzajRozkladu rozklad = new RodzajRozkladu();
            rozklad.setTypRozkladu(typRozkladu);
            rozklad.setLinia(linia);
            rozklad.setBrygada(brygada);
            rozklad.setGodzina(godzina);
            rozklad.setMiejsceZmiany(miejsceZmiany);
            rozklad.setPierwszaLinia(pierwszaLinia);
            this.rozkladRepository.save(rozklad);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public RodzajRozkladu getById(String id) {
        return rozkladRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        rozkladRepository.deleteById(id);
    }

    @Override
    public RodzajRozkladu save(RodzajRozkladu rodzajRozkladu) {
        rozkladRepository.save(rodzajRozkladu);
        return rodzajRozkladu;
    }

    @Override
    public List<RodzajRozkladu> findAll() {
        return rozkladRepository.findAll();
    }

}
