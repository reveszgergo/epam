package com.example.epam.epam;

public class Search {

    public boolean findByLocation(Car car, Location location){
        if(car.getLocation().getCity().equals(location.getCity())){
            return true;
        }
        return false;
    }
}
