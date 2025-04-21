package com.enterapp.enterapp.configuration;

import com.enterapp.enterapp.utility.DateUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.util.DateUtils;

@Configuration
public class ThymeleafConfig {
    @Bean
    public DateUtility dateUtils() {
        return new DateUtility();
    }
}
