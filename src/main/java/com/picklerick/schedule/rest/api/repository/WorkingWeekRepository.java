package com.picklerick.schedule.rest.api.repository;

import com.picklerick.schedule.rest.api.model.WorkingWeek;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkingWeekRepository extends CrudRepository<WorkingWeek, Long> {

    List<WorkingWeek> findByUserId(Long id);
    WorkingWeek findByStartDateAndUserId(LocalDate startDate, Long id);




}
