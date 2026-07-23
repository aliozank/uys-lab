package com.alikaracor.learning.rediscachelab.Service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AirlineLookupService {

    @Cacheable(cacheNames = "airlines", key = "#airlineId")
    public String getAirlineById(Long airlineId) {

        System.out.print(
                "ANA KAYNAK ÇALIŞTI - Airline ID: " + airlineId
        );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(exception);
        }

        return "Turkish Airlines - " + airlineId;
    }


    @CachePut(cacheNames = "airlines", key = "#airlineId")
    public String updateAirline(
            Long airlineId,
            String airlineName
    ) {
        System.out.println(
                "ANA KAYNAK GÜNCELLENDİ - Airline ID: " + airlineId
        );

        return airlineName + " - " + airlineId;
    }


    @CacheEvict(cacheNames = "airlines", key = "#airlineId")
    public void deleteAirline(Long airlineId) {

        System.out.println(
                "ANA KAYNAKTAN SİLİNDİ - Airline ID: " + airlineId
        );
    }
}