package com.example.epam.epam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CarController {

    @Autowired
    CarService carService;

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public List<Car> listAllCars(){
        List<Car> cars = carService.findAllCars();

        if(cars.isEmpty()){
            return new ArrayList<Car>();
        }

        return cars;
    }

    @RequestMapping(value = "/cars", method = RequestMethod.POST)
    public ResponseEntity<?> newCar(@RequestBody Car car, UriComponentsBuilder ucBuilder){
        carService.saveCar(car);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/cars/{id}").buildAndExpand(car.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/cars/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCar(@PathVariable("id") long id){
        Car car = carService.findById(id);

        if(car == null)
            return new ResponseEntity(new CustomError("User with id" + id + "not found."), HttpStatus.NOT_FOUND);

        carService.deleteCarById(id);

        return new ResponseEntity<Car>(HttpStatus.NO_CONTENT);
    }

}
