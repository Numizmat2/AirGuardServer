package com.airguard.airguard.measurement.service;

import com.airguard.airguard.AirlyClientService;
import com.airguard.airguard.entity.City;
import com.airguard.airguard.entity.Measurement;
import com.airguard.airguard.repository.CityRepository;
import com.airguard.airguard.repository.MeasurementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

@Service
public class AutomaticMeasurementsService {

    private final CityRepository cityRepository;
    private final MeasurementRepository measurementRepository;
    private final AirlyClientService airlyClientService;

    public AutomaticMeasurementsService(CityRepository cityRepository, MeasurementRepository measurementRepository, AirlyClientService airlyClientService) {
        this.cityRepository = cityRepository;
        this.measurementRepository = measurementRepository;
        this.airlyClientService = airlyClientService;
        this.executeHourlyDataGather();
    }

    public void executeHourlyDataGather() {
        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run () {
                gatherCitiesAirData();
            }
        };
        timer.schedule(hourlyTask, 0L, 1000*60*60*6);
    }

    public void gatherCitiesAirData() {
        var cities = this.getCities();
        cities.forEach(city -> {
            String response = "";
            var measurement = new Measurement();
            measurement.setTimestamp(LocalDateTime.now().toString());
            measurement.setCityId(city);
            try {
                response = airlyClientService.getDataByExternalSensorId(city.getSensorId());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HashMap parsedJsonMeasurement = null;
            try {
                parsedJsonMeasurement = new ObjectMapper().readValue(response, HashMap.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            var measurementValue = ((HashMap) parsedJsonMeasurement.get("current")).get("values").toString();
            measurement.setValue(measurementValue);
            measurementRepository.save(measurement);
        });
    }

    private List<City> getCities() {
        return cityRepository.findAll()
                .stream()
                .filter(x -> x.getSensorId() != null)
                .collect(Collectors.toList());
    }
}
