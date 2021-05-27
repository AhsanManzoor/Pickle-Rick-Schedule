package com.picklerick.schedule.rest.api.repository;

import com.picklerick.schedule.rest.api.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

        Iterable<Role> findAll();
    }
