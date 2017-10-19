package com.example.epam.epam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = {"http://localhost:4200"})
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

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCar(@RequestParam("id") String id){
        carRespository.delete(Long.parseLong(id));

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
