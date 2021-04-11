package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.model.User;
import com.picklerick.schedule.rest.api.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/user")
    public User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/user")
    Iterable<User> all(){
        return repository.findAll();
    }
    @GetMapping("/user/{id}")
    User one(@PathVariable Long id) throws Exception {
        return repository.findById(id).orElseThrow(()-> new Exception());
    }
    /*@PatchMapping("/user/{id}/checkin")
    public User checkinUser(@RequestBody User user, @PathVariable Long id) {
        return repository.findById(id)
        .map(u ->{ 
            user.setCheckinDate(new Date());
            return repository.save(u);
        })
                .orElseGet(() -> repository.save(user));
    }*/


}
