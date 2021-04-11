
package com.picklerick.schedule.rest.api.model;

import javax.persistence.*;
import java.util.Date;


/**
 * User
 * <p>
 * A user form User
 *
 */
@Entity
@Table(name="User")
public class User {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String lastname;
    private String firstname;
    //private Date checkinDate;

    public User(){}

    public User(String lastname, String firstname) {
        this.lastname = lastname;
        this.firstname = firstname;
    }

/**
     * the last name of the user
     *
     */
    public String getLastname() {
        return lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }*/

    /**
     * the last name of the user
     *
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * the firstname of the user
     *
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * the firstname of the user
     *
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(User.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("userId");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("lastname");
        sb.append('=');
        sb.append(((this.lastname == null)?"<null>":this.lastname));
        sb.append(',');
        sb.append("firstname");
        sb.append('=');
        sb.append(((this.firstname == null)?"<null>":this.firstname));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.firstname == null)? 0 :this.firstname.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.lastname == null)? 0 :this.lastname.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof User) == false) {
            return false;
        }
       User rhs = ((User) other);
        return ((((this.firstname == rhs.firstname)||((this.firstname!= null)&&this.firstname.equals(rhs.firstname)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.lastname == rhs.lastname)||((this.lastname!= null)&&this.lastname.equals(rhs.lastname))));
    }

}
