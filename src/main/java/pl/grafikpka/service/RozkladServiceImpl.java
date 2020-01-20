package pl.grafikpka.service;

import org.springframework.stereotype.Service;
import pl.grafikpka.model.RodzajRozkladu;
import pl.grafikpka.repository.RozkladRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RozkladServiceImpl implements RozkladService {
    private RozkladRepository rozkladRepository;

    public RozkladServiceImpl(RozkladRepository rozkladRepository) {
        this.rozkladRepository = rozkladRepository;
    }

    @Override
    public List<RodzajRozkladu> findAll() {
        return rozkladRepository.findAll();
    }

    @Override
    public boolean save(RodzajRozkladu rodzajRozkladu) {
        rozkladRepository.insert(rodzajRozkladu);
        return false;
    }

    String findAllByTypRozkladu(String typRozkladu, String startLine, String godz) {
        List<RodzajRozkladu> rozkladList = new ArrayList<>();
        rozkladRepository.findAll().forEach(rozkladList::add);
        return rozkladList.stream()
                .filter(r -> r.getTypRozkladu().equals(typRozkladu) &&
                        r.getLinia().equals(startLine)
                        && r.getGodzina().equals(godz))
                .map(RodzajRozkladu::getMiejsceZmiany).findAny().orElse("");

    }
}
