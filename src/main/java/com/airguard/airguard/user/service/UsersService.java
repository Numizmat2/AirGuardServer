package com.airguard.airguard.user.service;

import com.airguard.airguard.entity.City;
import com.airguard.airguard.model.ResponseLoginModel;
import com.airguard.airguard.repository.CityRepository;
import com.airguard.airguard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public UsersService(UserRepository userRepository, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    public List<ResponseLoginModel> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new ResponseLoginModel(user.getId(), user.getEmail(), user.getName(), user.getSex(),
                        user.getDefaultPollution() != null ? user.getDefaultPollution().getName() : null,
                        user.getCityOfResidence() != null ? user.getCityOfResidence().getName() : null))
                .collect(Collectors.toList());
    }

    public List<ResponseLoginModel> getAllUsersByCity(String cityName) {
        City city = cityRepository.findByName(cityName);
        return userRepository.getUsersByCityOfResidence(city)
                .stream()
                .map(user -> new ResponseLoginModel(user.getId(), user.getEmail(), user.getName(), user.getSex(),
                        user.getDefaultPollution() != null ? user.getDefaultPollution().getName() : null,
                        user.getCityOfResidence() != null ? user.getCityOfResidence().getName() : null))
                .collect(Collectors.toList());
    }
}
