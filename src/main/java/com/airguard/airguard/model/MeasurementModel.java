package com.airguard.airguard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MeasurementModel {
    @NotNull
    private Integer userId;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;
}
