package com.picklerick.schedule.rest.api.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="workingDay")
public class WorkingDay {


    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private LocalDate date;
    @Column(name="worked_time")
    private Double workedTime;
    @Column(name="scheduled_time")
    private Double scheduledTime;
    @Column(name="daily_difference")
    public Double dailyDifference;
    @Column(name="user_id")
    private Long userId;
    @Column(name="proceeded_week")
    private boolean proceededWeek;
    @Column(name="proceeded_month")
    private boolean proceededMonth;


    public boolean getProceededWeek() {
        return proceededWeek;
    }
    public void setProceededWeek(boolean proceededWeek){
        this.proceededWeek = proceededWeek;
    }
    public boolean getProceededMonth() {
        return proceededMonth;
    }
    public void setProceededMonth(boolean proceededMonth){
        this.proceededMonth = proceededMonth;
    }


    /**
     * Class constructor
     * @author Stefan
     * */
    public WorkingDay(){}
    /**
     * Class constructor with work specifications
     * @author Stefan
     * */
    public WorkingDay(LocalDate date, Double workedTime, Double scheduledTime, Double dailyDifference, Long user_id) {
        this.date = date;
        this.workedTime = workedTime;
        this.scheduledTime = scheduledTime;
        this.dailyDifference = dailyDifference;
        this.userId = user_id;

    }
    /**
     * Generated Get method for work id
     * @author Clelia
     * */
    public Long getId() {
        return id;
    }
    public Double getDailyDifference(){
        return dailyDifference;
    }

    public void setDailyTimeDifference(){
        this.dailyDifference = getWorkedTime() - getScheduledTime();
    }

    public Double getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Double weeklySchedule){
        Double time = weeklySchedule/5;
        this.scheduledTime= time;
    }

    /**
     * Generated Get method for date
     * @author Clelia
     * */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Generated Set method for date
     * @author Clelia
     *
     * @param date
     * */
    public void setDate(LocalDate date) {
        this.date = date;
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
        setDailyTimeDifference();
    }


    /**
     * Generated get method for user_id
     * @author Stefan
     * */
    public Long getUserId() {
        return userId;
    }

    /**
     * Generated set method for user_id
     * @author Stefan
     * @param user_id sets the user id for work entry
     * */
    public void setUserId(Long user_id) {
        this.userId = user_id;
    }




}

