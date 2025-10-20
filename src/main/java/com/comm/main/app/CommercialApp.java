package com.comm.main.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@ComponentScan(basePackages = {"com.comm.main","com.commercial.dqi","com.commercial.backend","com.comm.tudftoexcel","com.comm" +
		".reports","com.comm.ui"})
@SpringBootApplication
public class
CommercialApp {

	public static void main(String[] args) {

		SpringApplication.run(CommercialApp.class, args);
	}

}
