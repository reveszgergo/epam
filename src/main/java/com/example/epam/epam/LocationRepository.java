package com.example.epam.epam;


import com.example.epam.epam.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long>{

    Location findByCity(String city);
}
