package com.kasaiso.com.kasaisoapis.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestConfig {

    @Bean
    fun testRestTemplate(): TestRestTemplate {
        return TestRestTemplate()
    }
}