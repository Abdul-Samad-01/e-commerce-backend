package com.sellerbuyer.sellerbuyer.config;

import com.sellerbuyer.sellerbuyer.service.implementation.AuditorAwareImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditingConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImplementation();
    }
}

