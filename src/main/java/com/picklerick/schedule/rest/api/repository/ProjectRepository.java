package com.picklerick.schedule.rest.api.repository;

import com.picklerick.schedule.rest.api.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
