package com.example.epam.epam;

import com.example.epam.epam.entity.Car;
import com.example.epam.epam.entity.Location;

public class Search {

    public boolean findByLocation(Car car, Location location){
        if(car.getLocation().getCity().equals(location.getCity())){
            return true;
        }
        return false;
    }
}
