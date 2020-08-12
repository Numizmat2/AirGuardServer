package com.airguard.airguard.auth.service;

import com.airguard.airguard.Utils;
import com.airguard.airguard.entity.City;
import com.airguard.airguard.entity.PollutionType;
import com.airguard.airguard.entity.User;
import com.airguard.airguard.model.LoginModel;
import com.airguard.airguard.model.ResponseLoginModel;
import com.airguard.airguard.model.UserModel;
import com.airguard.airguard.repository.CityRepository;
import com.airguard.airguard.repository.PollutionTypeRepository;
import com.airguard.airguard.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final PollutionTypeRepository pollutionTypeRepository;

    public AuthService(UserRepository userRepository, CityRepository cityRepository, PollutionTypeRepository pollutionTypeRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.pollutionTypeRepository = pollutionTypeRepository;
    }

    @Transactional
    public void registerUser(UserModel user) {
        System.out.println("Starting registration");

        PollutionType pollutionType = null;
        City city = null;

        if (userRepository.getByEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already used.");
        }

        if (user.getDefaultPollution() != null ) {
            pollutionType = pollutionTypeRepository.findByName(user.getDefaultPollution().pollutionName());
        }

        if (user.getCityOfResidence() != null) {
            city = cityRepository.findByName(user.getCityOfResidence().cityName());
        }
        var newUser = new User(user.getEmail(), user.getName(), user.getPassword(),
                user.getSex(), pollutionType, city);

        userRepository.save(newUser);
        System.out.println("Registration completed!");
    }

    public ResponseLoginModel logInUser(LoginModel loginData) {
        System.out.println("Starting login");
        var user = userRepository.getByEmail(loginData.getEmail());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with provided email not found.");
        }
        if (user.getPassword().equals(Utils.getHashForPassword(loginData.getPassword()))) {
            System.out.println("Login completed!");
            return new ResponseLoginModel(user.getId(), user.getEmail(), user.getName(), user.getSex(),
                    user.getDefaultPollution() != null ? user.getDefaultPollution().getName() : null,
                    user.getCityOfResidence() != null ? user.getCityOfResidence().getName() : null);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials.");
        }
    }
}
