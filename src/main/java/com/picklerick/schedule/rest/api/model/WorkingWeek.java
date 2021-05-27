package com.picklerick.schedule.rest.api.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="workingWeek")
public class WorkingWeek {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private LocalDate startDate;
    @Column(name="worked_time")
    private Double workedTime;
    @Column(name="scheduled_time")
    private Double scheduledTime;
    @Column(name="weekly_difference")
    private Double weeklyDifference;
    @Column(name="user_id")
    private Long userId;

    /**
     * Class constructor
     * @author Stefan
     * */
    public WorkingWeek(){}
    /**
     * Class constructor with work specifications
     * @author Stefan
     * */
    public WorkingWeek(LocalDate startDate, LocalDate endDate, Double workedTime,Double scheduledTime,Double weeklyDifference, Long user_id) {
        this.startDate = startDate;
        this.workedTime = workedTime;
        this.scheduledTime = scheduledTime;
        this.weeklyDifference = weeklyDifference;
        this.userId = user_id;

    }
    /**
     * Generated Get method for work id
     * @author Stefan
     * */
    public Long getId() {
        return id;
    }

    /**
     * Generated Get method for date
     * @author Stefan
     * */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Generated Set method for date
     * @author Stefan
     *
     * @param date
     * */
    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }


    /**
     * Generated Get method for workedTime
     * @author Stefan
     * */
    public Double getWorkedTime() {
        return workedTime;
    }

    /**
     * Generated Set method for workedTime
     * @author Stefan
     *
     * @param workedTime which logs the time
     * */
    public void setWorkedTime(Double workedTime) {

        this.workedTime = workedTime;
        setWeeklyDifference();
    }


    /**
     * Generated get method for user_id
     * @author Stefan
     * */
    public Long getUser_id() {
        return userId;
    }

    /**
     * Generated set method for user_id
     * @author Stefan
     * @param user_id sets the user id for work entry
     * */
    public void setUser_id(Long user_id) {
        this.userId = user_id;
    }


    public Double getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Double scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Double getWeeklyDifference() {
        return weeklyDifference;
    }

    public void setWeeklyDifference() {
        this.weeklyDifference = getWorkedTime()- getScheduledTime();
    }
}



