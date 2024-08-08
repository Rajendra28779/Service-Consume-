package com.bskyserviceconsume.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 10/05/2023 - 12:06 PM
 */
@Configuration
public class LoggerConfig {

    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger(LoggerConfig.class);
    }
}
