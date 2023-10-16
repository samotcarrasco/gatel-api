package es.mdef.apigatel;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ApiGatelApp {
	public static final Logger log = LoggerFactory.getLogger(ApiGatelApp.class);

	public static void main(String[] args) {
//		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(ApiGatelApp.class, args);
	}

}
