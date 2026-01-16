package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 허용할 오리진 설정 (프론트엔드 주소)
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("http://localhost:*");
        
        // 허용할 HTTP 메서드 설정
        config.addAllowedMethod("*");
        
        // 허용할 헤더 설정
        config.addAllowedHeader("*");
        
        // 응답 헤더에 노출할 헤더 설정
        config.addExposedHeader("*");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

