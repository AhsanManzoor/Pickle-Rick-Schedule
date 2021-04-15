package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.repository.WorkRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkController {
    private final WorkRepository repository;

    /**
     * WorkController constructer initializing work repository
     * @author Clelia
     * */
    public WorkController(WorkRepository repository) {
        this.repository = repository;
    }
    
}
