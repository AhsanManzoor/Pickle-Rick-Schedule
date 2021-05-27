package com.picklerick.schedule.rest.api.repository;

import com.picklerick.schedule.rest.api.model.WorkingMonth;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkingMonthRepository extends CrudRepository<WorkingMonth, Long> {

    List<WorkingMonth> findByUserId(Long id);
    WorkingMonth findByStartDateAndUserId(LocalDate startDate, Long id);





}