package com.yourmd.phrases.configurations;

import org.slf4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by klemen on 16.1.2017.
 */
@Configuration
public class LoggerConfiguration {

    /**
     * Instantiates logger.
     *
     * @param injectionPoint
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Logger logger(DependencyDescriptor injectionPoint) {
        return org.slf4j.LoggerFactory.getLogger(injectionPoint.getField().getDeclaringClass());
    }
}
