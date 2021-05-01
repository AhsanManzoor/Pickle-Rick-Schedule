package com.picklerick.schedule.rest.api.controller;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    /**
     * @author Clelia
     * */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * @author Clelia
     * */
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request){
        request.getUserPrincipal();
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/overview";
        }
        return "redirect:/schedule";
    }
}
