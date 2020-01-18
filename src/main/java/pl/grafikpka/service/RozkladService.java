package pl.grafikpka.service;

import pl.grafikpka.model.RodzajRozkladu;

import java.util.List;

public interface RozkladService {
    List<RodzajRozkladu> findAll();

    boolean save(RodzajRozkladu rodzajRozkladu);
}
