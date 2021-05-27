package com.picklerick.schedule.rest.api.repository;

import com.picklerick.schedule.rest.api.model.WorkingDay;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkingDayRepository extends CrudRepository<WorkingDay, Long> {

    List<WorkingDay> findByUserId(Long id);
    WorkingDay findByDateAndUserId(LocalDate date, Long id);



}
