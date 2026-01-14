package com.investmentbanking.dealpipeline;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DealPipelineManagementApplicationTest {

    @Test
    void contextLoads() {
        // If Spring Boot context fails to load, this test fails.
        // This gives coverage to the @SpringBootApplication class.
        assertNotNull(this);
    }
}
