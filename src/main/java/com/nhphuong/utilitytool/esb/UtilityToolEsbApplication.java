package com.nhphuong.utilitytool.esb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.nhphuong.utilitytool.esb" })
public class UtilityToolEsbApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtilityToolEsbApplication.class, args);
	}

}
