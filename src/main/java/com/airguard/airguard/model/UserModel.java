package com.airguard.airguard.model;

import com.airguard.airguard.enums.Cities;
import com.airguard.airguard.enums.PollutionTypes;
import com.airguard.airguard.enums.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Valid
public class UserModel {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Nullable
    @Enumerated(EnumType.STRING)
    private PollutionTypes defaultPollution;
    @Nullable
    @Enumerated(EnumType.STRING)
    private Cities cityOfResidence;
}
