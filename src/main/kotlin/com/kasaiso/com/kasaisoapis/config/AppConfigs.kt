package com.kasaiso.com.kasaisoapis.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class AppConfigs {

    @Bean
    fun corsFilter(): CorsFilter{
        val config = CorsConfiguration()
        config.allowedOriginPatterns = listOf(
            "http://localhost:3000/"
        )
        config.addAllowedMethod(HttpMethod.HEAD)
        config.addAllowedMethod(HttpMethod.GET)
        config.addAllowedMethod(HttpMethod.POST)
        config.addAllowedMethod(HttpMethod.OPTIONS)
        config.addAllowedHeader("*")

        val configSource = UrlBasedCorsConfigurationSource()
        configSource.registerCorsConfiguration("/**", config)

        return CorsFilter(configSource)

    }
}