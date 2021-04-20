package com.picklerick.schedule.rest.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request){
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/overview/";
        }
        return "redirect:/schedule/";
    }
}
