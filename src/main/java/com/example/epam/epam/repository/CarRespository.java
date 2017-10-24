package com.example.epam.epam.repository;

import com.example.epam.epam.entity.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRespository extends CrudRepository<Car, Long> {

    List<Car> findByType(String type);
}
