package kr.sproutfx.common.logging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class LoggingApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoggingApplication.class, args);
	}
}
