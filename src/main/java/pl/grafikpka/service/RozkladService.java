package pl.grafikpka.service;

import pl.grafikpka.model.RodzajRozkladu;

import java.util.List;

public interface RozkladService{
    List<RodzajRozkladu> findAll();

    RodzajRozkladu save(RodzajRozkladu rodzajRozkladu);
}
