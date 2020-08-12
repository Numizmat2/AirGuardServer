package com.airguard.airguard.repository;

import com.airguard.airguard.entity.City;
import com.airguard.airguard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);
    List<User> getUsersByCityOfResidence(City city);
}
