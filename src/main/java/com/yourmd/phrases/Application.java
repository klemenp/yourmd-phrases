package com.yourmd.phrases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application bootstrap.
 * Microservice registration should eventually go in here.
 *
 * <p>
 * Created by klemen on 15.1.2017.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
