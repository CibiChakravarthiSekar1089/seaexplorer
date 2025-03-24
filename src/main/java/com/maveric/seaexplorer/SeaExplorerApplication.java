package com.maveric.seaexplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableCaching
@EnableWebSecurity
@EnableMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true
)
@SpringBootApplication
public class SeaExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeaExplorerApplication.class, args);
	}
}
