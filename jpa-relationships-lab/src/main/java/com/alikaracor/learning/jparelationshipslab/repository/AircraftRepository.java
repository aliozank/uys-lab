package com.alikaracor.learning.jparelationshipslab.repository;

import com.alikaracor.learning.jparelationshipslab.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    boolean existsByRegistrationNumberIgnoreCase(String registrationNumber);

    boolean existsByRegistrationNumberIgnoreCaseAndIdNot(
            String registrationNumber,
            Long id
    );

    boolean existsByOperatorAirline_Id(Long airlineId);

    List<Aircraft> findByOperatorAirline_Id(Long airlineId);

    @Query("""
            SELECT aircraft
            FROM Aircraft aircraft
            JOIN FETCH aircraft.operatorAirline
            """)
    List<Aircraft> findAllWithOperatorAirline();
}
