package com.example.epam.epam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CarController {

    @RequestMapping("/cars")
    public List<Car> cars(){

        Car car1 = new Car("Ferrari", 20000);
        Car car2 = new Car("Lamborghini", 15000);

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        return cars;
    }

}
