package com.picklerick.schedule.rest.api.model;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.RelationNotification;
import javax.management.relation.Role;


public class User_details implements UserDetails {

    private User user;

    public User_details(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = user.getRole_name();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (int i = 0; i < authorities.size(); i ++) {

            authorities.add(new SimpleGrantedAuthority(User.getRole_name()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return null;
    }



    public String getEmail() {
        return user.getEmail();
    }

    public Long getId() {
        return user.getId();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
