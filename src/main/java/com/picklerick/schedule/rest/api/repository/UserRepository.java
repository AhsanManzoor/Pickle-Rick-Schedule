package com.picklerick.schedule.rest.api.repository;

import com.picklerick.schedule.rest.api.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("Select u FROM user WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);
}
