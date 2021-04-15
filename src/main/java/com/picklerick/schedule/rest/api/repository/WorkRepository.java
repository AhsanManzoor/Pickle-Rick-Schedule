package com.picklerick.schedule.rest.api.repository;

import com.picklerick.schedule.rest.api.model.Work;
import org.springframework.data.repository.CrudRepository;

public interface WorkRepository extends CrudRepository<Work, Long> {
}
