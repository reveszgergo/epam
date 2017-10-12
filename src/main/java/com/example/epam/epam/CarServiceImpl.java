package com.example.epam.epam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("carService")
@Transactional
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRespository carRespository;

    @Override
    public Car findById(Long id) {
        if(id == null)
            return null;

        return carRespository.findOne(id);
    }

    @Override
    public void saveCar(Car car) {
        carRespository.save(car);
    }

    @Override
    public void deleteCarById(Long id) {
        carRespository.delete(id);
    }

    @Override
    public List<Car> findAllCars() {
        List<Car> cars = new ArrayList<>();

        for(Car car : carRespository.findAll()){
            cars.add(car);
        }

        return cars;
    }
}
