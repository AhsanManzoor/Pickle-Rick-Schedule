package com.picklerick.schedule.rest.api.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="workingMonth")
public class WorkingMonth {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(name="start_date")
    private LocalDate startDate;
    @Column(name="end_date")
    private LocalDate endDate;
    @Column(name="worked_time")
    private Double workedTime;
    @Column(name="scheduled_time")
    private Double scheduledTime;
    @Column(name="monthly_difference")
    private Double monthlyDifference;
    @Column(name="user_id")
    private Long userId;

    /**
     * Class constructor
     * @author Stefan
     * */
    public WorkingMonth(){}
    /**
     * Class constructor with work specifications
     * @author Stefan
     * */
    public WorkingMonth(LocalDate startDate, LocalDate endDate, Double workedTime, Double scheduledTime,
                        Double monthlyDifference,Long user_id) {
        this.startDate = startDate;
        this.workedTime = workedTime;
        this.scheduledTime = scheduledTime;
        this.monthlyDifference = monthlyDifference;
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
     * Generated Get method for date
     * @author Stefan
     * */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Generated Set method for date
     * @author Clelia
     *
     * @param date
     * */
    public void setEndDate(LocalDate date) {
        this.endDate = date;
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
        setMonthlyDifference();
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
        this.scheduledTime = scheduledTime*4;
    }

    public Double getMonthlyDifference() {
        return monthlyDifference;
    }

    public void setMonthlyDifference() {

        this.monthlyDifference = (getWorkedTime() - getScheduledTime());
    }
}


