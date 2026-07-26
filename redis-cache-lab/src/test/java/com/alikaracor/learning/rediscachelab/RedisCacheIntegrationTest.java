package com.alikaracor.learning.rediscachelab;

import com.alikaracor.learning.rediscachelab.Service.AirlineLookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class RedisCacheIntegrationTest {

    @Container
    static final GenericContainer<?> REDIS =
            new GenericContainer<>(
                    DockerImageName.parse("redis:7-alpine")
            ).withExposedPorts(6379);

    @DynamicPropertySource
    static void configureRedis(
            DynamicPropertyRegistry registry
    ) {
        registry.add(
                "spring.data.redis.host",
                REDIS::getHost
        );

        registry.add(
                "spring.data.redis.port",
                () -> REDIS.getMappedPort(6379)
        );
    }

    @Autowired
    private AirlineLookupService airlineLookupService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void clearRedis() {
        redisTemplate
                .getConnectionFactory()
                .getConnection()
                .serverCommands()
                .flushDb();
    }

    @Test
    void shouldCacheAndEvictAirline() {

        airlineLookupService.getAirlineById(1L);

        Boolean keyExists =
                redisTemplate.hasKey("airlines::1");

        assertThat(keyExists).isTrue();

        airlineLookupService.deleteAirline(1L);

        Boolean keyExistsAfterDelete =
                redisTemplate.hasKey("airlines::1");

        assertThat(keyExistsAfterDelete).isFalse();
    }

    @Test
    void shouldUpdateCachedAirline() {

        airlineLookupService.updateAirline(
                2L,
                "Pegasus Airlines"
        );

        Cache airlinesCache =
                cacheManager.getCache("airlines");

        assertThat(airlinesCache).isNotNull();

        String cachedAirline =
                airlinesCache.get(2L, String.class);

        assertThat(cachedAirline)
                .isEqualTo("Pegasus Airlines - 2");
    }
}