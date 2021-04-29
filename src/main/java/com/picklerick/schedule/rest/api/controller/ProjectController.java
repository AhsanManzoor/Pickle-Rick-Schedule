package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.repository.ProjectRepository;

public class ProjectController {
    private final ProjectRepository repository;

    /**
     * ProjectController constructor initializing Project repository
     * @author Yomiyou
     * */
    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }
}
