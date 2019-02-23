package com.unw.crypto;

import com.unw.crypto.test.Forwardtest;
import java.text.ParseException;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


@EntityScan(
        basePackageClasses = {TestApplication.class, Jsr310JpaConverters.class}
)
//@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) throws BeansException, ParseException {
		ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);
		
		context.getBean(Forwardtest.class).forwardtest();
	}
}
