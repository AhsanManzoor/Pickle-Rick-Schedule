package com.picklerick.schedule.rest.api.controller;


import com.picklerick.schedule.rest.api.config.EmailService;
import com.picklerick.schedule.rest.api.model.Login;
import com.picklerick.schedule.rest.api.model.User;
import com.picklerick.schedule.rest.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;
import java.util.Random;

/**
 * credits to (LearnCode with Durgesh) at: https://www.youtube.com/playlist?list=PL0zysOflRCelmjxj-g4jLr3WKraSU_e8q
 * Forgot password controller and different form related to it
 * also the content of the email with the verification token
 * @author Ahsan
 * */


@Controller
public class ForgotPasswordController {


    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordController.class);
    Random random = new Random();
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder BCryptPasswordEncoder;

    @RequestMapping("/forgot")
    public String openEmailForm() {
        return "forgotPassword";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("resetEmail") String email, HttpSession session) {
        //generate token
        int token = random.nextInt(99999999);

        String subject = "Email Reset token from Pickle_rick time recorder";
        String message = "Dear User," + "\n" + "\n" + "Your Requested Reset Password Token =" + token + "\n" + "If you did not request the reset password token please ignore this email!" + "\n" + "\n" + "Support@Pickle_rick Time Recorder";
        String to = email;

        boolean flag = this.emailService.sendEmail(subject, message, to);

        if (flag) {
            session.setAttribute("mytoken", token);
            session.setAttribute("email", email);

            return "verify";

        } else {
            session.setAttribute("m3", "Check your email ID!!");

            return "forgotPassword";
        }
    }

    // token Verification
    @PostMapping("/tokenVerify")
    public String verifyToken(@RequestParam("token") int token, HttpSession session) {
        int mytoken = (int) session.getAttribute("mytoken");
        String email = (String) session.getAttribute("email");
        if (mytoken == token) {
            //password change form
            Optional<User> user = this.repository.findByEmail(email);
            if (!user.isPresent()) {
                //send error message
                session.setAttribute("m4", "The email ID you have entered does not exist in the DataBase !!");

                return "forgotPassword";
            } else {
                // send change password form
                return "ChangePassword";
            }
        } else {
            session.setAttribute("m5", "You have entered the wrong token!!");
            return "verify";
        }
    }

    /**
     * Forgot password reset
     *
     * @author Ahsan
     * */
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("confirmNewPassword") String newPassword, HttpSession session) {
        String email = (String) session.getAttribute("email");
        Optional<User> user = this.repository.findByEmail(email);
        User user2 = user.get();
        Login l2 = user2.getLogin();
        l2.setPassword(this.BCryptPasswordEncoder.encode(newPassword));
        this.repository.save(user2);

        // if password changed successfully return to the login page
        session.setAttribute("m6","Password change successful!!");

        return "index";

    }

    /**
     * Change password in settings
     *
     * @author Ahsan
     * */
    @PostMapping("/reset_Password")
    public String resetPassword(@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword") String newPassword, Principal principle, HttpSession session){
        String userName =principle.getName();
        Optional<User> loggedUser = this.repository.findByEmail(userName);
        User usr3 = loggedUser.get();
        Login l3 = usr3.getLogin();

        if(this.BCryptPasswordEncoder.matches(oldPassword, l3.getPassword())){
            //change password
            l3.setPassword(this.BCryptPasswordEncoder.encode(newPassword));
            this.repository.save(usr3);
            session.setAttribute("m1", "Your password has been changed successfully!!");
            LOGGER.info("Password changed in Database!!");
            return "/settings";
        }else{
            session.setAttribute("m2", "The old password does not match!!");

            return "redirect:/settings";
        }

    }

}
