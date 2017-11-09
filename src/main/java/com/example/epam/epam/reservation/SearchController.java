package com.example.epam.epam.reservation;

import com.example.epam.epam.Car;
import com.example.epam.epam.CarRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/search")
public class SearchController {

    @Autowired
    private CarRespository carRespository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Car>> getAvailableCars(){

        List<Car> cars = new ArrayList<>();
        for(Car car : carRespository.findAll()){
            if(car.getUser().getFirstName().isEmpty() && car.getUser().getLastName().isEmpty()){
                cars.add(car);
            }
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @RequestMapping(value = "/by_location", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> filterByLocation(@RequestParam(value = "location") String location){

        List<Car> cars = new ArrayList<>();
        for(Car car : carRespository.findAll()){
            if(location.equals(car.getLocation().getCity())){
                cars.add(car);
            }
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @RequestMapping(value = "/by_price", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> filterByPrice(@RequestParam(value = "min_price") Integer minPrice, @RequestParam(value = "max_price") Integer maxPrice){

        List<Car> cars = new ArrayList<>();
        for(Car car : carRespository.findAll()){
            if(minPrice <= car.getPrice() && maxPrice >= car.getPrice()){
                cars.add(car);
            }
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @RequestMapping(value = "/by_type", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> filterByType(@RequestParam(value = "type") String type){
        List<Car> cars = new ArrayList<>();
        for(Car car : carRespository.findAll()){
            if(type.equals(car.getType())){
                cars.add(car);
            }
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @RequestMapping(value = "/by_time", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> filterByTime(@RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @RequestParam(value = "until") @DateTimeFormat(pattern = "yyyy-MM-dd") Date untilDate) throws Exception{
        List<Car> cars = new ArrayList<>();
        for(Car car : carRespository.findAll()){
            if((fromDate.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(car.getFromDate())) < 0 && untilDate.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(car.getUntilDate())) < 0) ||
                    (fromDate.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(car.getFromDate())) > 0 && untilDate.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(car.getUntilDate())) >0)){
                cars.add(car);
            }
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

}
