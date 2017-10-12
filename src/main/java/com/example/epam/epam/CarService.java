package com.example.epam.epam;

import java.util.List;

public interface CarService {

    Car findById(Long id);

    void saveCar(Car car);

    void deleteCarById(Long id);

    List<Car> findAllCars();
}
