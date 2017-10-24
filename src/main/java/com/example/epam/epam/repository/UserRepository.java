package com.example.epam.epam.repository;

import com.example.epam.epam.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{

    User findUserByUsername(String username);
}
