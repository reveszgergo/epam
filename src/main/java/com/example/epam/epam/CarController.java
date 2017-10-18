package com.example.epam.epam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRespository carRespository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Car>> getCars(){
        return new ResponseEntity<>(carRespository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addCar(@RequestBody Car car){
        return new ResponseEntity<>(carRespository.save(car), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCar(@PathVariable long id){
        carRespository.delete(id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
