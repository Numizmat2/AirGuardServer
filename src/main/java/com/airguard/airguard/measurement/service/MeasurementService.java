package com.airguard.airguard.measurement.service;

import com.airguard.airguard.AirlyClientService;
import com.airguard.airguard.entity.City;
import com.airguard.airguard.entity.Measurement;
import com.airguard.airguard.model.MeasurementModel;
import com.airguard.airguard.model.MeasurmentResponseModel;
import com.airguard.airguard.repository.CityRepository;
import com.airguard.airguard.repository.MeasurementRepository;
import com.airguard.airguard.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final AirlyClientService airlyClientService;

    public MeasurementService(MeasurementRepository measurementRepository, UserRepository userRepository, CityRepository cityRepository, AirlyClientService airlyClientService) {
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.airlyClientService = airlyClientService;
    }

    public MeasurmentResponseModel addNewMeasurement(MeasurementModel measurementModel) throws InterruptedException, IOException, URISyntaxException {
        System.out.println("latitude " + measurementModel.getLatitude());
        System.out.println("longitude " + measurementModel.getLongitude());
        var user = userRepository.findById(measurementModel.getUserId()).get();
        var measurement = new Measurement();
        measurement.setTimestamp(LocalDateTime.now().toString());
        measurement.setUserId(user);
        ArrayList parsedJson = getParsedJsonSensor(measurementModel);
        Integer sensorId = null;
        if (parsedJson.size() > 0) {
            var sensorInfo = (HashMap) parsedJson.get(0);
            sensorId = (Integer) sensorInfo.get("id");
            var address = ((HashMap) sensorInfo.get("address")).get("street").toString();
            var city = ((HashMap) sensorInfo.get("address")).get("city").toString().toLowerCase();
            City foundCity = cityRepository.findByName(city);
            measurement.setCityId(foundCity);
            measurement.setAddress(address);
        }

        measurement.setValue(getMeasurementValueFromAirly(measurementModel));
        if (measurement.getValue().equals("[]") && sensorId != null) {
            measurement.setValue(getMeasurementValueFromAirlyBySensorId(sensorId));
        }
        measurementRepository.save(measurement);
        return this.createMeasurementResponseObject(measurement);
    }

    private String getMeasurementValueFromAirly(MeasurementModel measurementModel) throws IOException, InterruptedException, URISyntaxException {
        var measurementResponse = airlyClientService.getPointMeasurements(measurementModel.getLongitude(), measurementModel.getLatitude());
        var parsedJsonMeasurement = new ObjectMapper().readValue(measurementResponse, HashMap.class);
        return ((HashMap) parsedJsonMeasurement.get("current")).get("values").toString();
    }

    private String getMeasurementValueFromAirlyBySensorId(Integer sensorId) throws IOException, InterruptedException, URISyntaxException {
        var measurementResponse = airlyClientService.getDataByExternalSensorId(sensorId);
        var parsedJsonMeasurement = new ObjectMapper().readValue(measurementResponse, HashMap.class);
        return ((HashMap) parsedJsonMeasurement.get("current")).get("values").toString();
    }

    private ArrayList getParsedJsonSensor(MeasurementModel measurementModel) throws IOException, InterruptedException, URISyntaxException {
        var sensorResponse = airlyClientService.getNearestSensorInfo(measurementModel.getLongitude(), measurementModel.getLatitude());
        return new ObjectMapper().readValue(sensorResponse, ArrayList.class);
    }

    private MeasurmentResponseModel createMeasurementResponseObject(Measurement measurement) {
        return new MeasurmentResponseModel(measurement);
    }

    public List<MeasurmentResponseModel> getMeasurementsByUserId(Integer userId) {
        var user = userRepository.findById(userId).orElseThrow();
        return measurementRepository.findAllByUserIdOrderByIdDesc(user)
                .stream()
                .filter(result -> result.getValue() != "[]")
                .map(MeasurmentResponseModel::new)
                .collect(Collectors.toList());
    }

    public MeasurmentResponseModel getLastMeasurementByUserId(Integer userId) {
        var user = userRepository.findById(userId).orElseThrow();
        return new MeasurmentResponseModel(measurementRepository.findFirstByUserIdOrderByIdDesc(user));
    }

    public List<MeasurmentResponseModel> getMeasurementsByCityName(String cityName) {
        var city = cityRepository.findByName(cityName);
        return measurementRepository.findTop25ByCityIdOrderByIdDesc(city)
                .stream()
                .filter(result -> result.getValue() != "[]")
                .map(MeasurmentResponseModel::new)
                .collect(Collectors.toList());
    }

    public MeasurmentResponseModel getLastMeasurementByCityName(String cityName) {
        var city = cityRepository.findByName(cityName);
        return new MeasurmentResponseModel(measurementRepository.findFirstByCityIdOrderByIdDesc(city));
    }

    public List<MeasurmentResponseModel> getAll() {
        return measurementRepository.findAll()
                .stream()
                .filter(result -> result.getValue() != "[]")
                .map(MeasurmentResponseModel::new)
                .collect(Collectors.toList());
    }
}
