package com.example.epam.epam;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRespository extends CrudRepository<Car, Long> {

    List<Car> findByType(String type);
}
