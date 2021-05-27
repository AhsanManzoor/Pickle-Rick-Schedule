package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.model.Login;
import com.picklerick.schedule.rest.api.model.User;
import com.picklerick.schedule.rest.api.model.WorkingWeek;
import com.picklerick.schedule.rest.api.repository.UserRepository;
import com.picklerick.schedule.rest.api.repository.WorkingWeekRepository;
import com.picklerick.schedule.rest.api.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;


@RestController
public class UserController {

    private final UserRepository repository;
    private final WorkingWeekRepository workingWeekRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    public UserController(UserRepository repository, WorkingWeekRepository workingWeekRepository) {
        this.repository = repository;
        this.workingWeekRepository = workingWeekRepository;
    }


    /**
     * Returns a list with all users
     * only an Admin user has access to all users
     *
     * @author Clelia
     * */
    @ModelAttribute("users")
    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    Iterable<User> all(Model model){
        LocalDate monday = LocalDate.now();
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        model.addAttribute("users", repository.findAll());

        ArrayList<WorkingWeek> workSummary = new ArrayList<>();

        Iterable<User> allUser = repository.findAll();
        for (User user : allUser ) {
            WorkingWeek week = workingWeekRepository.findByStartDateAndUserId(monday, user.getId());
            workSummary.add(week);
        }
        model.addAttribute("work", workSummary);
        return repository.findAll();
    }

    /**
     * Returns a user with a specific id
     *
     * @author: Clelia
     * @param id the id of the user to retrieve
     * */
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.userId == #id")
    @GetMapping("/users/{id}")
    User one(@PathVariable Long id, Authentication authentication) throws AccessDeniedException {
        return repository.findById(id).orElseThrow(()-> new AccessDeniedException("Unauthorized - Request"));
    }

    /**
     * Update a users information
     *
     * @author: Clelia & Stefan
     * @param id id of user updating their information
     * */
    @Secured("ROLE_ADMIN")
    @PatchMapping("/users/{id}")
    public User updateUserAsAdmin(@RequestBody Map<String, Object> userUpdates, @PathVariable Long id) {
        User user = repository.findById(id).get();
        // Fetch User data from db and
        // go through all the possible options of change
        // and save the changes to the user
        return repository.findById(id)
                .map(u -> {
                    userUpdates.forEach(
                            (update, value)-> {
                                switch (update) {
                                    case "firstname":
                                        u.setFirstname((String) value);
                                        break;
                                    case "lastname":
                                        u.setLastname((String) value);
                                        break;
                                    case "email":
                                        u.setEmail((String) value);
                                        break;
                                    case "weekly_schedule":
                                        u.setWeeklySchedule((Double.parseDouble((String) value)));
                                        break;
                                    case "manager_id":
                                        u.setManagerId((Long.parseLong((String) value)));
                                        break;
                                    // TODO solve role issue + create such a method for normal users
                                }
                            }
                    );
                    return repository.save(u);
                }).orElseGet(() -> repository.save(user));

    }

    /**
     * Create new user
     *
     * @author Clelia
     * */
    @Secured("ROLE_ADMIN")
    @PostMapping("/user")
    public void addNewUser(@ModelAttribute User user, Model model, Authentication authentication, HttpServletResponse response) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        user.setManagerId(userDetails.getUserId());
        Login login = user.getLogin();
        login.setUser(user);
        login.setPassword(this.bCryptPasswordEncoder.encode(login.getPassword()));
        model.addAttribute("user", user);
        repository.save(user);
        LOGGER.info("New user "+ user.getFirstname()+" "+ user.getLastname()+" was saved to the database");
        response.sendRedirect("/users");
    }
}