package com.example.epam.epam;

import com.example.epam.epam.authentication.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{

    User findUserByUsername(String username);
}
