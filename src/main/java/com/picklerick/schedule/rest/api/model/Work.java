package com.picklerick.schedule.rest.api.model;

import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name="work")
public class Work {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private LocalDate date;
    @Column(name="start_at")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startAt;
    @Column(name="end_at")
    private LocalTime endAt;
    @Column(name="worked_time")
    private Double workedTime;
    @Column(name="user_id")
    private Long userId;
    @Column(name="proceeded")
    private boolean proceeded;

    /**
     * Class constructor
     * @author Clelia
     * */
    public Work(){}
    /**
     * Class constructor with work specifications
     * @author Clelia
     * */
    public Work(LocalDate date, LocalTime start_at, LocalTime end_at, Double workedTime, Long user_id, Boolean proceeded) {
        this.date = date;
        this.startAt = start_at;
        this.endAt = end_at;
        this.workedTime = workedTime;
        this.userId = user_id;
        this.proceeded = proceeded;

    }
    /**
     * Generated Get method for work id
     * @author Clelia
     * */
    public Long getId() {
        return id;
    }

    /**
     * Generated Get method for date
     * @author Clelia
     * */
    public LocalDate getDate() {
        return date;
    }

    public boolean getProceeded() {
        return proceeded;
    }
    public void setProceeded(boolean proceeded){
        this.proceeded = proceeded;
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
    }

    /**
     * Generated Get method for start_at
     * @author Clelia
     * */
    public LocalTime getStart_at() {
        return startAt;
    }

    /**
     * Generated Set method for start_at
     * @author Clelia
     *
     * @param start_at which logs the time
     * */
    public void setStart_at(LocalTime start_at) {
        this.startAt = start_at;
    }


    /**
     * Generated Get method for end_at
     * @author Clelia
     * */
    public LocalTime getEnd_at() {
        return endAt;
    }

    /**
     * Generated set method for end_at
     * @author Clelia
     *
     * @param endAt logs the end time
     * */
    public void setEndAt(LocalTime endAt) {
        this.endAt = endAt;
    }

    /**
     * Generated get method for user_id
     * @author Clelia
     * */
    public Long getUser_id() {
        return userId;
    }

    /**
     * Generated set method for user_id
     * @author Clelia
     *
     * @param user_id sets the user id for work entry
     * */
    public void setUser_id(Long user_id) {
        this.userId = user_id;
    }
}
