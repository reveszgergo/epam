package com.example.epam.epam.reservation;

import com.example.epam.epam.Car;
import com.example.epam.epam.CarRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reservation")
public class ReservationController {

    @Autowired
    private CarRespository carRespository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Car>> getReservations(){

        List<Car> cars = new ArrayList<>();
        for(Car car : carRespository.findAll()){
            if( !car.getUser().getFirstName().isEmpty() && !car.getUser().getLastName().isEmpty()){
                cars.add(car);
            }
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> markNotReserved(@RequestBody Car car){

        Car completedCar = carRespository.findOne(car.getId());
        completedCar.getUser().setFirstName("");
        completedCar.getUser().setLastName("");
        completedCar.setFromDate("");
        completedCar.setUntilDate("");
        carRespository.save(completedCar);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/change_city", method = RequestMethod.POST)
    public ResponseEntity<Void> changeLocation(@RequestBody Car car, @RequestParam(value = "location") String location){

        Car changeCar = carRespository.findOne(car.getId());
        changeCar.getLocation().setCity(location);
        carRespository.save(changeCar);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public ResponseEntity<Void> reserve(@RequestBody Car car){

        Car reservedCar = carRespository.findOne(car.getId());
        reservedCar.getUser().setFirstName(car.getUser().getFirstName());
        reservedCar.getUser().setLastName(car.getUser().getLastName());
        reservedCar.setFromDate(car.getFromDate());
        reservedCar.setUntilDate(car.getUntilDate());
        carRespository.save(reservedCar);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
