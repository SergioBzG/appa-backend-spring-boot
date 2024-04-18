package com.sbz.appa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppaApplication {
	public static void main(String[] args) {
//		EnvLoaderConfig.loadEnv();
		SpringApplication.run(AppaApplication.class, args);
	}

}
