package org.grabber.lg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.grabber.lg")
public class LgApplication {

	public static void main(String[] args) {
		SpringApplication.run(LgApplication.class, args);
	}

}
