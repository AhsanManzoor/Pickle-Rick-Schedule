package com.picklerick.schedule.rest.api.config;

import com.picklerick.schedule.rest.api.controller.IndexController;
import com.picklerick.schedule.rest.api.controller.WorkController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final WorkController workController;
    private final IndexController indexController;

    @Autowired
    private UserDetailsService customUserDetailsService;

    public SecurityConfig(WorkController workController, IndexController indexController) {
        this.workController = workController;
        this.indexController = indexController;
    }

    /**
     * Encypt password with BCryptPasswordEncoder with 12 rounds
     *
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
                .passwordEncoder(passwordEncoder())
                .and()
                .inMemoryAuthentication();
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
                .antMatchers("/", "/forgot","/sendEmail","/tokenVerify","/changePassword")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .formLogin()
                    .loginPage("/")
                    .loginProcessingUrl("/perform_login")
                    .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                        workController.startTimeRecording(authentication);
                    })
                    .defaultSuccessUrl("/default")
                    .failureUrl("/?error")
                    .permitAll()
                .and()
                .logout()
                /*
                 * ONLY THE logoutSuccessHandler METHOD IS FROM STEFAN!
                 * @author Stefan
                 * Code partly from https://www.codejava.net/frameworks/spring-boot/spring-security-logout-success-handler-example
                 * (13.05.2021)
                 * */
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    indexController.logLogoutOfUser(authentication);
                    workController.endTimeRecording(authentication);
                   httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
                })

                .permitAll()
                // allow iframes from the same host to render on page
                .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin()
           .and().csrf().disable();
    }

}

