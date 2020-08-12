package com.airguard.airguard.repository;

import com.airguard.airguard.entity.PollutionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollutionTypeRepository extends JpaRepository<PollutionType, Integer> {
    PollutionType findByName(String name);
}
