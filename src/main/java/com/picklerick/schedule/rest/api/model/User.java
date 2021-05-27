
package com.picklerick.schedule.rest.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="user")
public class User {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String lastname;
    private String firstname;
    private String email;
    @Column(name = "weekly_schedule")
    private Double weeklySchedule;
    @Column(name = "manager_id")
    private Long managerId;

    @ManyToMany(cascade = CascadeType.MERGE, fetch= FetchType.EAGER)
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
     * Class constructor with user specifications
     * @author Clelia
     * */
    public User(String lastname, String firstname, String email, Double weeklySchedule, Long managerId, List<Role> roles, Login login) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.weeklySchedule = weeklySchedule;
        this.managerId = managerId;
        this.roles = roles;
        this.login = login;
    }

    public User() {
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
    public Double getWeeklySchedule() {
        return weeklySchedule;
    }

    /**
     * Generated Set method for weekly_schedule
     * @author Clelia
     *
     * @param weekly_schedule time estimate to work*/
    public void setWeeklySchedule(Double weekly_schedule) {
        this.weeklySchedule = weekly_schedule;
    }
    /**
     * Generated Get method for user's manager id
     * @author Clelia
     * */
    public Long getManagerId() {
        return managerId;
    }

    /**
     * Generated Set method for manager id
     * @author Clelia
     *
     * @param managerId refers to the user's manager*/
    public void setManagerId(Long managerId) {
        this.managerId = managerId;
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
     * @param roles displays the roles a user has -> what access they should be granted*/
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

}
