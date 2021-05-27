package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.model.User;
import com.picklerick.schedule.rest.api.repository.UserRepository;
import com.picklerick.schedule.rest.api.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;
    private final WorkController workController;
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    public IndexController(WorkController workController) {
        this.workController = workController;
    }

    /**
     * @author Clelia
     * */
    @RequestMapping("/")
    public String index() {

        return "index";
    }

    /**
     * Redirect Admin user to the overview page and redirect normal users to the schedule page
     *
     * @author Clelia
     * */
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request, Authentication authentication, Principal principle){
        workController.startTimeRecording(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

       User user = userRepository.findById(userId).get();

        request.getUserPrincipal();
        if (request.isUserInRole("ROLE_ADMIN")) {

            LOGGER.info(user.getFirstname()+" "+user.getLastname() + "is an admin and has logged in!!");
            return "redirect:/overviewAdmin";
        }

        LOGGER.info(user.getFirstname()+" "+user.getLastname() + "is a user and has logged in!");
        return "redirect:/schedule";
    }

    /**
     * Log logout
     *
     * @author Clelia
     * */
    public void logLogoutOfUser(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        User user = userRepository.findById(userId).get();
        LOGGER.info(user.getFirstname() + " " + user.getLastname() + " logged out" );
    }

}
