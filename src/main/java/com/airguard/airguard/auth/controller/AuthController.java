package com.airguard.airguard.auth.controller;

import com.airguard.airguard.auth.service.AuthService;
import com.airguard.airguard.model.LoginModel;
import com.airguard.airguard.model.ResponseLoginModel;
import com.airguard.airguard.model.UserModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public @ResponseBody String register(@Valid @RequestBody UserModel user) {
        authService.registerUser(user);
        return "Registration completed";
    }

    @PostMapping(value = "/login")
    public @ResponseBody
    ResponseLoginModel login(@Valid @RequestBody LoginModel loginData) {
        return authService.logInUser(loginData);
    }
}
