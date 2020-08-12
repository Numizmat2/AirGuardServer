package com.airguard.airguard.user.controller;

import com.airguard.airguard.model.ResponseLoginModel;
import com.airguard.airguard.user.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public @ResponseBody
    List<ResponseLoginModel> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping(value = "/{cityName}")
    public @ResponseBody
    List<ResponseLoginModel> getAllUsersByCity(@PathVariable String cityName) {
        return usersService.getAllUsersByCity(cityName);
    }
}
