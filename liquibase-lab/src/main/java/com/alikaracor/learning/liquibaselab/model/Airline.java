package com.alikaracor.learning.liquibaselab.model;

import jakarta.persistence.*;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "airlines")
public class Airline {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "iata_code", nullable = false, unique = true, length = 2)
    private String iataCode;

    @Column(name = "icao_code", nullable = false, unique = true, length = 3)
    private String icaoCode;

    public AirlineStatus getStatus() {
        return status;
    }

    public void setStatus(AirlineStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AirlineStatus status;

    public Airline() {}
}
