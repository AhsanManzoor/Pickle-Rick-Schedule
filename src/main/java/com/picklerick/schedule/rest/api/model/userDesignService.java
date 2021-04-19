package com.picklerick.schedule.rest.api.model;

import com.picklerick.schedule.rest.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class userDesignService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User_details loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.loadUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user email!");
        }

        return new User_details(user);
    }
}
