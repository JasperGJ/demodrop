package com.hexagon.demodrop.repository;

import com.hexagon.demodrop.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
