package com.example.epam.epam;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long>{

    Location findByCity(String city);
}
