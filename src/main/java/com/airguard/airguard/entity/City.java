package com.airguard.airguard.entity;

import com.airguard.airguard.enums.Cities;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="city")
@Data
@NoArgsConstructor
public class City {

    public City(Cities name) {
        this.name = name.cityName();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Integer id;
    @NotNull
    private String name;
    @Column(name="stations_count")
    @Nullable
    private Integer stationsCount;
    @Column(name="average_pollution")
    @Nullable
    private String averagePollution;
    @Nullable
    @Column(name="sensor_id")
    private Integer sensorId;

    public String getName() {
        if (this.name == null) {
            return "EMPTY";
        }
        return this.name;
    }
}
