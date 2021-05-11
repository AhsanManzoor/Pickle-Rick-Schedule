package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.model.Login;
import com.picklerick.schedule.rest.api.model.Role;
import com.picklerick.schedule.rest.api.model.User;
import com.picklerick.schedule.rest.api.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.*;


@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }


    /**
     * Returns a list with all users
     * only an Admin user has access to all users
     * @author Clelia
     **/
    @ModelAttribute("users")
    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    Iterable<User> all(Model model){
        model.addAttribute("users", repository.findAll());
        return repository.findAll();
    }

    /**
     * Returns a user with a specific id
     * @author: Clelia
     * @param id the id of the user to retrieve
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.userId == #id")
    @GetMapping("/users/{id}")
    User one(@PathVariable Long id, Authentication authentication) throws AccessDeniedException {

        return repository.findById(id).orElseThrow(()-> new AccessDeniedException("Unauthorized - ALARM - INTRUDER - CALL SECURITY - I NEED A DRINK"));

    }


    /**
     * Update a users information
     * @author: Clelia & Stefan
     *
     * @param id id of user updating their information
     */
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
                                        u.setWeekly_schedule((Double.parseDouble((String) value)));
                                        break;
                                    case "manager_id":
                                        u.setManager_id((Long.parseLong((String) value)));
                                        break;
                                    // TODO solve role issue + create such a method for normal users
                                }
                            }
                    );
                    return repository.save(u);
                }).orElseGet(() -> repository.save(user));

    }

    /**
     * Get all users created by admin
     * @authors Clelia,
     *
     * @param id admin-id
     */
    @GetMapping("/users/admin/{id}")
    Iterable<User> allByAdmin(@PathVariable Long id) {
        //TODO find all users created by admin
        return repository.findAll();
    }

    /**
     * Create new user
     * @author Clelia
     * */
    @Secured("ROLE_ADMIN")
    @PostMapping("/users")
    public User addNewUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }


    /*
     * Redirecting page
     * @author Yomiyou
     * */
    /*@GetMapping("/getAdmin-add-employeeView")
    public RedirectView AddEmployeeView() {
        return  new RedirectView("/Admin-add-employee.html");
    }*/

    /**
     * Create new Employee by Admin
     * @author Yomiyou
     *
     * @param newEmployee User data, temp_password temporary password
     * */
    @Secured("ROLE_ADMIN")
    @PostMapping("/newEmployee")
    public Object addNewEmployee(@RequestBody User newEmployee,
                                 @RequestParam("temp_password") String password,
                                 @RequestParam("role1") Role role1, @RequestParam("role2") Role role2
    ) {
        Optional<User> foundEmployee = repository.findByEmail(newEmployee.getEmail());
        if (foundEmployee != null) {
            return ResponseEntity.badRequest().build();
        }
        // TODO We need a checkbox in the HTML to choose different Role type (Just User, Manager...)

        // Collect the List of Employee Roles from the Form Input and save them in the newEmployee
        /*
        List<Role> roles = new List<Role>();
        if (role1 != null) { roles.add(role1);}
        if (role2 != null) { roles.add(role2);}
        newEmployee.setRoles(roles);
        */

        // Create a Login entity based on the entered temporary password
        Login newLogin = new Login();
        newLogin.setId(newEmployee.getId());
        newLogin.setPassword(new BCryptPasswordEncoder().encode(password));
        newEmployee.setLogin(newLogin);

        //Save New employee to the user repository
        return repository.save(newEmployee);
    }

    /**
     * Delete Existing user
     * @author Yomiyou
     *
     * @param userId user-id
     * */
    @DeleteMapping("/user/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        User user = repository.findById(userId)
                .orElseThrow(()-> new Exception());
        repository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    //------------------------- Not in scope yet ----------------------------
    /**
     * Change setting of a user
     * @author Clelia
     *
     * @param id user id
     * */

    //TODO does not update email, weekly_schedule, manager_id or is_admin, why?

  /*
  @Secured("ROLE_ADMIN")
  @PatchMapping("/users/{id}")
    public User changeUserData(@PathVariable Long id, @RequestBody Map<String, Object> userUpdates){
        // get saved user as fallback option
        User user = repository.findById(id).get();
        // Fetch User data from db and
        // go through all the possible options of change
        // and save the changes to the user
        return repository.findById(id)
                .map(u -> {
                    userUpdates.forEach(
                            (update, value) -> {
                                switch (update){
                                    case "firstname": u.setFirstname((String) value); break;
                                    case "lastname": u.setLastname((String) value); break;
                                    case "email": u.setEmail((String) value); break;
                                    case "weekly_schedule": u.setWeekly_schedule((Double) value); break;
                                    case "manager_id": u.setManager_id((Long) value); break;
                                    case "is_admin": u.setIs_admin((Boolean) value); break;
                                }
                            });
                    return repository.save(u);
                })
                .orElseGet(()-> repository.save(user));
    }
    This is for test purposes
       @PostMapping("/user")
    public User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }
    @PatchMapping("/user/{id}/checkin")
    public User checkinUser(@RequestBody User user, @PathVariable Long id) {
        return repository.findById(id)
        .map(u ->{
            user.setCheckinDate(new Date());
            return repository.save(u);
        })
                .orElseGet(() -> repository.save(user));
    }*/

}