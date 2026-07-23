package com.alikaracor.learning.jparelationshipslab.repository;

import com.alikaracor.learning.jparelationshipslab.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    boolean existsByIataCodeIgnoreCase(String iataCode);

    boolean existsByIcaoCodeIgnoreCase(String icaoCode);

    boolean existsByIataCodeIgnoreCaseAndIdNot(String iataCode, Long id);

    boolean existsByIcaoCodeIgnoreCaseAndIdNot(String icaoCode, Long id);
}
