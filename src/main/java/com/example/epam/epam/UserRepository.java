package com.example.epam.epam;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>{

    List<User> findByFirstName(String firstName);
}
