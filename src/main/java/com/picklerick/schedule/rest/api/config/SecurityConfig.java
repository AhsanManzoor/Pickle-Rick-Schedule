package com.picklerick.schedule.rest.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true, prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;

    /**
     * Encypt password with BCryptPasswordEncoder with 12 rounds
     * @author Clelia
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * @author Clelia
     * */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * Configure Security setup
     * @author Clelia
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // any request outside of the root content is requiring authentication
        // further is access to the form login on the login page and the logout page permitted.
        http
                .authorizeRequests()
                // enable css and js
                .antMatchers("/js/**", "/css/**", "/images/**").permitAll()
                .antMatchers("/")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/default")
                .failureUrl("/?error")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                // allow iframes from the same host to render on page
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                // for postman we need to create an entry point to authorize requests
                .and().httpBasic().realmName("REALM_PICKLE").authenticationEntryPoint(entryPoint())
                //remove after postman usage
                .and().csrf().disable();
    }

    /**
     * Add entry point for postman to access with basic authentication configurations
     * @author Clelia
     * */
    public BasicAuthenticationEntryPoint entryPoint(){
        BasicAuthenticationEntryPoint basic = new BasicAuthenticationEntryPoint();
        basic.setRealmName("REALM_PICKLE");
        return basic;
    }

}
