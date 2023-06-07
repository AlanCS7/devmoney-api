package com.alancss.devmoneyapi;

import com.alancss.devmoneyapi.config.properties.DevmoneyApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DevmoneyApiProperty.class)
public class DevmoneyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevmoneyApiApplication.class, args);
    }

}
