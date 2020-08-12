package com.airguard.airguard.repository;

import com.airguard.airguard.entity.City;
import com.airguard.airguard.entity.Measurement;
import com.airguard.airguard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findAll();
    List<Measurement> findAllByUserIdOrderByIdDesc(User user);
    List<Measurement> findTop25ByCityIdOrderByIdDesc(City city);
    Measurement findFirstByCityIdOrderByIdDesc(City city);
    Measurement findFirstByUserIdOrderByIdDesc(User user);
}
