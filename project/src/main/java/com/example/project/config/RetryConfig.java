package com.example.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

@EnableRetry // retry 사용 어노테이션
@Configuration
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        return new RetryTemplate();
    }

}
