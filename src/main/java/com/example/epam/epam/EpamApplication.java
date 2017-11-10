package com.example.epam.epam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootApplication
public class EpamApplication {

    private static final Logger log = LoggerFactory.getLogger(EpamApplication.class);

    private static final String DEFAULT_DATE = "2000-01-01";

	public static void main(String[] args) {
		SpringApplication.run(EpamApplication.class, args);
	}

	/*@Bean
    public CommandLineRunner demo(CarRespository repository){

	    return (args) -> {

            Location loc1 = new Location("London");
            Location loc2 = new Location("Paris");
            Location loc3 = new Location("Debrecen");

            User user1 = new User("Pista", "Kiss");
            User user2 = new User("Géza", "Affleck");
            User user3 = new User("", "");

            repository.save(new Car("Ferrari", 1000,"2017-11-09", "2017-11-15", loc1, user1));
            repository.save(new Car("Bugatti", 2000,"2017-11-10", "2017-11-20", loc2, user2));
            repository.save(new Car("Toyota", 3000, DEFAULT_DATE, DEFAULT_DATE, loc3, user3));

            log.info("Cars found:");
            log.info("-------------------------");
            for(Car car : repository.findAll()){
                log.info(car.getType());
                log.info(car.getLocation().getCity());
                log.info(car.getPrice().toString());
                log.info(car.getUser().getFirstName());
            }
            log.info("");

        };
    }*/
}
