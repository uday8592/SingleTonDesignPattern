package com.weather.detaiils.config;

import com.weather.detaiils.lagacy.LegacyConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LegacyConfigAdapter {
    @Bean
    public LegacyConfig legacyConfig() {
        // returning the enum INSTANCE; Spring will treat this as a singleton bean
        return LegacyConfig.INSTANCE;
    }
}
