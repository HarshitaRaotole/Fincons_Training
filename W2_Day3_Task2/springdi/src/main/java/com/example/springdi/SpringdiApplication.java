package com.example.springdi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringdiApplication {

	public static void main(String[] args) {
		ApplicationContext context =
				SpringApplication.run(SpringdiApplication.class,args);
		Car car = context.getBean(Car.class);
		car.drive();
	}

}
