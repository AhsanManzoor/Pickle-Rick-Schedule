package com.picklerick.schedule.rest.api.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="work")
public class Work {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private Date date;
    private Time start_at;
    private Time end_at;
    private Long user_id;
    private Long project_id;

    /**
     * Class constructor
     * @author Clelia
     * */
    public Work(){}

    /**
     * Class constructor with work specifications
     * @author Clelia
     * */
    public Work(Date date, Time start_at, Time end_at, Long user_id) {
        this.date = date;
        this.start_at = start_at;
        this.end_at = end_at;
        this.user_id = user_id;
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
    public Date getDate() {
        return date;
    }

    /**
     * Generated Set method for date
     * @author Clelia
     *
     * @param date
     * */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Generated Get method for start_at
     * @author Clelia
     * */
    public Time getStart_at() {
        return start_at;
    }

    /**
     * Generated Set method for start_at
     * @author Clelia
     *
     * @param start_at which logs the time
     * */
    public void setStart_at(Time start_at) {
        this.start_at = start_at;
    }

    /**
     * Generated Get method for end_at
     * @author Clelia
     * */
    public Time getEnd_at() {
        return end_at;
    }

    /**
     * Generated set method for end_at
     * @author Clelia
     *
     * @param end_at logs the end time
     * */
    public void setEnd_at(Time end_at) {
        this.end_at = end_at;
    }

    /**
     * Generated get method for user_id
     * @author Clelia
     * */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * Generated set method for user_id
     * @author Clelia
     *
     * @param user_id sets the user id for work entry
     * */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * Generated get method for project_id
     * @author Clelia
     * */
    public Long getProject_id() {
        return project_id;
    }

    /**
     * Generated set method for project_id
     * @author Clelia
     *
     * @param project_id sets project id which ties logged work to a project
     * */
    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }
}
