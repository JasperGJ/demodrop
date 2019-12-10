package com.hexagon.demodrop.repository;

import com.hexagon.demodrop.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
