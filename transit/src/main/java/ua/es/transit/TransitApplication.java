package ua.es.transit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TransitApplication {
	public static void main(String[] args) {
		SpringApplication.run(TransitApplication.class, args);
	}
}
