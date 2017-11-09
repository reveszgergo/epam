package com.example.epam.epam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class EpamApplication {

    private static final Logger log = LoggerFactory.getLogger(EpamApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EpamApplication.class, args);
	}

	@Bean
    public CommandLineRunner demo(CarRespository repository){

	    return (args) -> {

            Location loc1 = new Location("London");
            Location loc2 = new Location("Paris");
            Location loc3 = new Location("Debrecen");

            User user1 = new User("Pista", "Kiss");
            User user2 = new User("Géza", "Affleck");
            User user3 = new User("", "");

            repository.save(new Car("Ferrari", 1000, "", "", loc1, user1));
            repository.save(new Car("Bugatti", 2000, "", "",  loc2, user2));
            repository.save(new Car("Toyota", 3000, "", "", loc3, user3));

            Search search = new Search();

            log.info("Cars found:");
            log.info("-------------------------");
            for(Car car : repository.findAll()){
                if(search.findByLocation(car, loc1)) {
                    log.info(car.getType());
                    log.info(car.getLocation().getCity());
                    log.info(car.getPrice().toString());
                    log.info(car.getUser().getFirstName());
                }
            }
            log.info("");

        };
    }
}
