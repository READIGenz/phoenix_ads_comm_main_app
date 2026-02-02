package com.comm.main.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = {"com.comm.main","com.commercial.dqi","com.commercial.backend","com.comm.tudftoexcel","com.comm" +
		".reports","com.comm.ui"})
@EntityScan(basePackages = "com.commercial.dqi.entity")
@EnableJpaRepositories(basePackages = "com.commercial.dqi.repository")
@SpringBootApplication
public class
CommercialApp {

	public static void main(String[] args) {

		SpringApplication.run(CommercialApp.class, args);
	}

}
