package com.airguard.airguard.entity;

import com.airguard.airguard.Utils;
import com.airguard.airguard.enums.Sex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.hash.Hashing;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

    public User(String email, String name, String password, Sex sex, PollutionType defaultPollution, City cityOfResidence) {
        this.email = email;
        this.name = name;
        this.password = this.getHashedPassword(password);
        this.sex = sex;
        this.defaultPollution = defaultPollution;
        this.cityOfResidence = cityOfResidence;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Integer id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String name;
    @NotNull
    @JsonIgnore
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Nullable
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="default_pollution")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PollutionType defaultPollution;
    @Nullable
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name="city")
    private City cityOfResidence;

    public void setPassword(String password) {
        this.password = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

    private String getHashedPassword(String password) {
        return Utils.getHashForPassword(password);
    }

}
