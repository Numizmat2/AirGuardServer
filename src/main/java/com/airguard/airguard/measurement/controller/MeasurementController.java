package com.airguard.airguard.measurement.controller;

import com.airguard.airguard.measurement.service.MeasurementService;
import com.airguard.airguard.model.MeasurementModel;
import com.airguard.airguard.model.MeasurmentResponseModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("measurements")
public class MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping(value = "/new")
    public MeasurmentResponseModel addUserMeasurement(@Valid @RequestBody MeasurementModel measurement) throws InterruptedException, IOException,
            URISyntaxException {
        return measurementService.addNewMeasurement(measurement);
    }

    @GetMapping("/all")
    public List<MeasurmentResponseModel> getAllMeasurements() {
        return measurementService.getAll();
    }

    @GetMapping(value = "/user/{userId}")
    public List<MeasurmentResponseModel> getMeasurementsByUserId(@PathVariable Integer userId) {
        return measurementService.getMeasurementsByUserId(userId);
    }

    @GetMapping(value = "/city/{cityName}")
    public List<MeasurmentResponseModel> getMeasurementsByCityName(@PathVariable String cityName) {
        return measurementService.getMeasurementsByCityName(cityName);
    }

    @GetMapping(value = "/user/{userId}/last")
    public MeasurmentResponseModel getLastMeasurementByUserId(@PathVariable Integer userId) {
        return measurementService.getLastMeasurementByUserId(userId);
    }

    @GetMapping(value = "/city/{cityName}/last")
    public MeasurmentResponseModel getLastMeasurementByCityName(@PathVariable String cityName) {
        return measurementService.getLastMeasurementByCityName(cityName);
    }

    @GetMapping(value = "/city/recent")
    public List<MeasurmentResponseModel> getRecentCitiesData() {
        return measurementService.getRecentCityData();
    }
}
