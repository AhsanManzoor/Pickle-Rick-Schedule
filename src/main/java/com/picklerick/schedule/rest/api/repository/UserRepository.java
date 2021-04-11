package com.picklerick.schedule.rest.api.repository;

import com.picklerick.schedule.rest.api.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
