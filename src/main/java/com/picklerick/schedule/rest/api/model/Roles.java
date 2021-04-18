package com.picklerick.schedule.rest.api.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @Column (name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String role_name;

    public Integer getId() {
        return id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

}
