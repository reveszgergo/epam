package com.example.epam.epam;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface CarRespository extends CrudRepository<Car, Long> {

    Collection<Car> findAll();
}
