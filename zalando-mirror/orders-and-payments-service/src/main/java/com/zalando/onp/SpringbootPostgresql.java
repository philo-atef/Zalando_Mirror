package com.zalando.onp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringbootPostgresql {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootPostgresql.class, args);
    }

}