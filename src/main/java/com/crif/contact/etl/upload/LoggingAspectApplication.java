package com.crif.contact.etl.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ImportResource;

/**
 * The Class LoggingAspectApplication.
 */
@SpringBootApplication
@ConfigurationPropertiesScan("com.crif.contact.etl.upload.config")
@ImportResource("classpath:aop-application-context.xml")
public class LoggingAspectApplication {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(LoggingAspectApplication.class, args);
	}
}

