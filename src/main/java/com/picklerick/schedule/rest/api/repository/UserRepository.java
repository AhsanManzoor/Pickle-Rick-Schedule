package com.picklerick.schedule.rest.api.repository;

import com.picklerick.schedule.rest.api.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
