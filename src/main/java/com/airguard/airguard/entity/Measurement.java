package com.airguard.airguard.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "measurement")
@Data
@NoArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Integer id;
    private String timestamp;
    @Nullable
    private String value;
    @Nullable
    private String address;
    @ManyToOne(fetch= FetchType.LAZY, cascade=CascadeType.MERGE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name="user_id")
    @Nullable
    private User userId;
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name="city_id")
    private City cityId;
}
