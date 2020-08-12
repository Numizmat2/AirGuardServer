package com.airguard.airguard.entity;

import com.airguard.airguard.enums.PollutionTypes;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="pollution_type")
@Data
@NoArgsConstructor
public class PollutionType {

    public PollutionType(PollutionTypes name) {
        this.name = name.pollutionName();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Integer id;
    @NotNull
    private String name;
    @Nullable
    private String norm;

    public String getName() {
        if (this.name == null) {
            return "EMPTY";
        }
        return this.name;
    }
}
