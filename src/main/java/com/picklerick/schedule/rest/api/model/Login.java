package com.picklerick.schedule.rest.api.model;

import javax.persistence.*;

@Entity
@Table(name="login")
public class Login {

    private @Id @Column(name="user_id") Long id;
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private User user;

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
     * Generated Get method for Password
     * @author Clelia
     **/
    public String getPassword(){
        return password;
    }

    /**
     * Generate Set method for setting new password
     * @author Clelia
     *
     * @param password new password
     * */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Generated Get method for user relation
     * @author Clelia
     * */
    public User getUser() {
        return user;
    }

    /**
     * Generated Set method for user
     * @author Clelia
     *
     * @param user User for password*/
    public void setUser(User user) {
        this.user = user;
    }

}
