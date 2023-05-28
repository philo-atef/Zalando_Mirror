package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@RestController
@SpringBootApplication


@RequestMapping("/api/v1/demo-controller")
@EnableDiscoveryClient
public class AuthenticationApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

	@GetMapping
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("Hello from secured endpoint");
	}
}
