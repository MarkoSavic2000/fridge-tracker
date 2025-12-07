package com.fridge.tracker.boot.configuration;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {
        "com.fridge.tracker.boot",
        "com.fridge.tracker.rest",
        "com.fridge.tracker.application",
        "com.fridge.tracker.domain",
        "com.fridge.tracker.persistence",
        "com.fridge.tracker.external.interfaces"

})
@EntityScan(basePackages = "com.fridge.tracker.persistence")
@EnableJpaRepositories(basePackages = "com.fridge.tracker.persistence.sql")
public class ApplicationConfiguration {
}