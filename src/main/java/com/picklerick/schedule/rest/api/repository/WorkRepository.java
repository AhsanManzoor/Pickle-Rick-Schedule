package com.picklerick.schedule.rest.api.repository;

import com.picklerick.schedule.rest.api.model.Work;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkRepository extends CrudRepository<Work, Long>{
    List<Work> findByUserId(Long id);
    List<Work> findByDateAndUserId(LocalDate date, Long id);



}
