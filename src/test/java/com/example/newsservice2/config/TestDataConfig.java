package com.example.newsservice2.config;

import com.example.newsservice2.testUtils.TestDbFacade;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestDataConfig {
    @Bean
    public TestDbFacade testDbFacade() {
        return new TestDbFacade();
    }
}
