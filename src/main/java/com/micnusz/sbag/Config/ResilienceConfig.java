package com.micnusz.sbag.Config;


import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id ->
                new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(
                                io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                                        .failureRateThreshold(50)
                                        .slidingWindowSize(10)
                                        .waitDurationInOpenState(Duration.ofSeconds(10))
                                        .permittedNumberOfCallsInHalfOpenState(2)
                                        .build()
                        )
                        .timeLimiterConfig(
                                TimeLimiterConfig.custom()
                                        .timeoutDuration(Duration.ofSeconds(2))
                                        .build()
                        )
                        .build()
        );
    }
}
