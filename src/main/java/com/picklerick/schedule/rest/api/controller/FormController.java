package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.model.Login;
import com.picklerick.schedule.rest.api.model.User;
import com.picklerick.schedule.rest.api.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FormController {

    private final RoleRepository roleRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(FormController.class);


    public FormController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Create Models and load all Roles to select in the Add New User Form
     *
     * @author Clelia
     * */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String newUser(Model model) {
        LOGGER.info("Attempt started to create new user");
        User user = new User();
        user.setLogin(new Login());
        Login login = user.getLogin();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("login", login);

        return "addNewUser";
    }

}
