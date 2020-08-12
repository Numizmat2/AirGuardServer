package com.airguard.airguard.model;

import com.airguard.airguard.entity.City;
import com.airguard.airguard.entity.Measurement;
import com.airguard.airguard.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurmentResponseModel {

    public MeasurmentResponseModel(Measurement measurement) {
        this.id = measurement.getId();
        this.timestamp = measurement.getTimestamp();
        if (measurement.getAddress() != null && !measurement.getAddress().isBlank()) {
            this.address = measurement.getAddress();
        }
        if (measurement.getCityId() != null) {
           this.city = measurement.getCityId().getName();
        }
        if (measurement.getUserId() != null) {
            this.user = measurement.getUserId().getId();
        }
        this.value = measurement.getValue();
    }

    private Integer id;
    private String timestamp;
    @Nullable
    private String value;
    @Nullable
    private String address;
    @Nullable
    private Integer user;
    @Nullable
    private String city;
}
