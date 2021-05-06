
package com.picklerick.schedule.rest.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


/**
 * User
 * <p>
 * A user form User
 *
 */
@Entity
@Table(name="user")
public class User {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String lastname;
    private String firstname;
    private String email;
    private Double weekly_schedule;
    private Long manager_id;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Login login;

    /**
     * Class constructor
     * @author Clelia
     * */
    public User(){}

    /**
     * Class constructor with user specifications
     * @author Clelia
     * */
    public User(String lastname, String firstname, String email, Double weekly_schedule, Long manager_id, List<Role> roles, Login login) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.weekly_schedule = weekly_schedule;
        this.manager_id = manager_id;
        this.roles = roles;
        this.login = login;
    }

    /**
     * Generated Get method for id
     * @author Clelia
     * */
    public Long getId() {
        return id;
    }

    /**
     * Generated Set method for Lastname
     * @author Clelia
     *
     * @param id id to set
     * */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Generated Get method for Lastname
     * @author Clelia
     * */
    public String getLastname() {
        return lastname;
    }

    /**
     * Generated Set method for Lastname
     * @author Clelia
     *
     * @param lastname lastname to set
     * */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Generated Get method for Firstname
     * @author Clelia
     * */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Generated Set method for Firstname
     * @author Clelia
     *
     * @param firstname firstname to set
     * */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Generated Get method for Email
     * @author Clelia
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Generated Set method for Email
     * @author Clelia
     *
     * @param email Email address to set
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Generated Get method for weekly_schedule
     * @author Clelia
     * */
    public Double getWeekly_schedule() {
        return weekly_schedule;
    }

    /**
     * Generated Set method for weekly_schedule
     * @author Clelia
     *
     * @param weekly_schedule time estimate to work*/
    public void setWeekly_schedule(Double weekly_schedule) {
        this.weekly_schedule = weekly_schedule;
    }
    /**
     * Generated Get method for user's manager id
     * @author Clelia
     * */
    public Long getManager_id() {
        return manager_id;
    }

    /**
     * Generated Set method for manager id
     * @author Clelia
     *
     * @param manager_id refers to the user's manager*/
    public void setManager_id(Long manager_id) {
        this.manager_id = manager_id;
    }

    /**
     * Generated Get method to see what roles a user has
     * @author Clelia
     * */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Generated Set the different roles a user can have
     * @author Clelia
     *
     * @param roles displays the roles a user has -> what access they should be grantedr*/
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * Generate get method for Login of user
     * @author Clelia
     * */
    public Login getLogin() {
        return login;
    }

    /**
     * Generated set method for Login of user
     * @author Clelia
     * */
    public void setLogin(Login login) {
        this.login = login;
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
    /**
     * Generated set method for Login of user
     * @author Clelia
     * */
    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.firstname == null)? 0 :this.firstname.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.lastname == null)? 0 :this.lastname.hashCode()));
        return result;
    }
    /**
     * @author Clelia
     * */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof User) == false) {
            return false;
        }
        User rhs = ((User) other);
        return ((((this.firstname == rhs.firstname)||((this.firstname!= null)
                &&this.firstname.equals(rhs.firstname)))&&((this.id == rhs.id)||((this.id!= null)
                &&this.id.equals(rhs.id))))&&((this.lastname == rhs.lastname)||((this.lastname!= null)
                &&this.lastname.equals(rhs.lastname))));
    }

}
