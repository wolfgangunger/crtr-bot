package com.unw.crypto;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootApp {

	public static void main(String[] args) throws BeansException, IOException {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootApp.class, args);
		
		//context.getBean(CsvToDatabaseWriter.class).importCsvToDatabase();
               context.getBean(App.class).run();
	}
}
