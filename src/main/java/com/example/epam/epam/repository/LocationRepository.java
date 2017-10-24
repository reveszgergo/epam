package com.example.epam.epam.repository;


import com.example.epam.epam.entity.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long>{

    Location findByCity(String city);
}
