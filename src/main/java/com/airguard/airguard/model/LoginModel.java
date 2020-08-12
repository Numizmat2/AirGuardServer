package com.airguard.airguard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Valid
public class LoginModel {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}
