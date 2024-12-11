package org.example.lab4b.Service;

import org.example.lab4b.Model.Rate;
import org.example.lab4b.Repository.RateRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RateService {

    private final RateRepository rateRepository = new RateRepository();

    public Rate createRate(Rate rate) {
        return rateRepository.save(rate);
    }

    public List<Rate> findAll() {
        return rateRepository.findAll();
    }

    public Map<String, Object[]> getRateStatistics() {
        List<Object[]> rawStats = rateRepository.getStatistics();
        return rawStats.stream().collect(
                Collectors.toMap(
                        row -> (String) row[0],
                        row -> new Object[] {
                                row[1],
                                row[2],
                        }
                )
        );
    }
}
