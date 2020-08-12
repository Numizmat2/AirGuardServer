package com.airguard.airguard.model;

import com.airguard.airguard.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLoginModel {
    @NotNull
    private Integer id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Nullable
    private String defaultPollution;
    @Nullable
    private String cityOfResidence;
}
