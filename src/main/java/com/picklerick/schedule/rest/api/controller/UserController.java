package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.model.User;
import com.picklerick.schedule.rest.api.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }



    @GetMapping("/users")
    Iterable<User> all(){
        return repository.findAll();
    }

    /**
    * @author: Clelia
     * Returns a user with a specific id
     *
     * @param id the id of the user to retrieve
     *
    */
    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) throws Exception {
        return repository.findById(id).orElseThrow(()-> new Exception());
    }

    /**
     * @author: Clelia
     * Update a users information
     *
     * @param id
     */
    @PatchMapping("/users/{id}/{firstname}/{lastname}")
    public User updateUser(@RequestBody User user, @PathVariable Long id, @PathVariable String firstname, @PathVariable String lastname) {
        return repository.findById(id)
                .map(u -> {
                    u.setFirstname(firstname);
                    u.setLastname(lastname);
                    return repository.save(u);
                }).orElseGet(() -> repository.save(user));
    }
    /*

    This is for test purposes
       @PostMapping("/user")
    public User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @PatchMapping("/user/{id}/checkin")
    public User checkinUser(@RequestBody User user, @PathVariable Long id) {
        return repository.findById(id)
        .map(u ->{ 
            user.setCheckinDate(new Date());
            return repository.save(u);
        })
                .orElseGet(() -> repository.save(user));
    }*/


}
