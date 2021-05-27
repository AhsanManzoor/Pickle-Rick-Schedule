package com.picklerick.schedule.rest.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    /**
     * Generated get method for role id
     * @author Clelia
     * */
    public Long getId() {
        return id;
    }

    /**
     * Generated Set method for role id
     * @author Clelia
     *
     * @param id id of role
     * */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Generated Get method for role name
     * @author Clelia
     * */
    public String getName() {
        return name;
    }

    /**
     * Generated Set method for role name
     * @author Clelia
     *
     * @param name description of role
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Generated Get method for List of users
     * @author Clelia
     * */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Generated Set method for List of Users
     *
     * @param users list of users
     * @author Clelia
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

}
