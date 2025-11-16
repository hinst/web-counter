package org.hinst.web.counter;

import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class WebCounterApplication {
	private Logger logger = LoggerFactory.getLogger(WebCounterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WebCounterApplication.class, args);
	}

	@PostConstruct
	public void runAfterStartup() {
		logger.info("Started in timezone: {} {}", ZonedDateTime.now().getZone(), ZonedDateTime.now().getOffset());
	}
}
