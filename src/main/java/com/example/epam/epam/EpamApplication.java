package com.example.epam.epam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EpamApplication {

    private static final Logger log = LoggerFactory.getLogger(EpamApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EpamApplication.class, args);
	}

	@Bean
    public CommandLineRunner demo(CarRespository repository){
	    return (args) -> {
	        repository.save(new Car("Ferrari", 100000));
            repository.save(new Car("Renault", 1000));
            repository.save(new Car("Jaguar", 50000));

            log.info("Cars found with findAll():");
            log.info("-------------------------");
            for(Car car : repository.findAll()){
                log.info(car.toString());
            }
            log.info("");

        };
    }
}
