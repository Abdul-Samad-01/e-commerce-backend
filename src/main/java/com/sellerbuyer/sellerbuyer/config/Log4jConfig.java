package com.sellerbuyer.sellerbuyer.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Log4jConfig {

    @Bean
    public Logger getLogger() {
        return LogManager.getLogger(getClass());
    }
}


