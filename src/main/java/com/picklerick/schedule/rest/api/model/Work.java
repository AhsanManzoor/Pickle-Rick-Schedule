package com.picklerick.schedule.rest.api.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="work")
public class Work {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private Date date;
    @Column(name="start_at")
    private Time startAt;
    @Column(name="end_at")
    private Time endAt;
    @Column(name="user_id")
    private Long userId;
    @Column(name="project_id")
    private Long projectId;

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
        this.startAt = start_at;
        this.endAt = end_at;
        this.userId = user_id;
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
        return startAt;
    }

    /**
     * Generated Set method for start_at
     * @author Clelia
     *
     * @param start_at which logs the time
     * */
    public void setStart_at(Time start_at) {
        this.startAt = start_at;
    }

    /**
     * Generated Get method for end_at
     * @author Clelia
     * */
    public Time getEnd_at() {
        return endAt;
    }

    /**
     * Generated set method for end_at
     * @author Clelia
     *
     * @param end_at logs the end time
     * */
    public void setEnd_at(Time end_at) {
        this.endAt = end_at;
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

    /**
     * Generated get method for project_id
     * @author Clelia
     * */
    public Long getProject_id() {
        return projectId;
    }

    /**
     * Generated set method for project_id
     * @author Clelia
     *
     * @param project_id sets project id which ties logged work to a project
     * */
    public void setProject_id(Long project_id) {
        this.projectId = project_id;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Work.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("workId");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("startAt");
        sb.append('=');
        sb.append(((this.startAt == null)?"<null>":this.startAt));
        sb.append(',');
        sb.append("endAt");
        sb.append('=');
        sb.append(((this.endAt == null)?"<null>":this.endAt));
        sb.append(',');
        sb.append("userId");
        sb.append('=');
        sb.append(((this.userId == null)?"<null>":this.userId));
        sb.append(',');
        sb.append("projectId");
        sb.append('=');
        sb.append(((this.projectId == null)?"<null>":this.projectId));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
    /**
     * Generated set method for Login of user
     * @author Clelia
     * */
    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.date == null)? 0 :this.date.hashCode()));
        result = ((result* 31)+((this.startAt == null)? 0 :this.startAt.hashCode()));
        result = ((result* 31)+((this.endAt == null)? 0 :this.endAt.hashCode()));
        result = ((result* 31)+((this.userId == null)? 0 :this.userId.hashCode()));
        result = ((result* 31)+((this.projectId == null)? 0 :this.projectId.hashCode()));
        return result;
    }
    /*
     * @author Clelia
     *
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof User) == false) {
            return false;
        }
        User rhs = ((User) other);
        return ((((this.date == rhs.date)||((this.date!= null)
                &&this.date.equals(rhs.date)))&&((this.id == rhs.id)||((this.id!= null)
                &&this.id.equals(rhs.id))))&&((this.lastname == rhs.lastname)||((this.lastname!= null)
                &&this.lastname.equals(rhs.lastname))));
    }*/

}
