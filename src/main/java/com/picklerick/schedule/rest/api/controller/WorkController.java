package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.model.Work;
import com.picklerick.schedule.rest.api.repository.WorkRepository;
import com.picklerick.schedule.rest.api.security.CustomUserDetails;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;




@RestController
@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,  MediaType.ALL_VALUE})
public class WorkController {
    private final WorkRepository repository;

    /**
     * WorkController constructer initializing work repository
     *
     * @author Clelia
     */
    public WorkController(WorkRepository repository) {
        this.repository = repository;
    }

   /*  @RequestMapping(value = "/callWorkTimeCalculator", method = RequestMethod.GET)
    public String callWorkTimeCalculator() {


        WorkController.workingTimeCalculator();

    } */



    /**
     * Calculate working time (between check-in and check-out)
     * @param in check-in, out check-out
     * @author Stefan
     */
    // I still need to do the test
    // Output type questionable
    /* @RequestMapping(value = "/workingTimeCalculator", method=RequestMethod.POST)
     */

    public static Double workingTimeCalculator(Time in, Time out) {

        // Transforming to LocalDate for method internal processing
        LocalTime inT = in.toLocalTime();
        LocalTime outT = out.toLocalTime();
        // In documentation defined end time, if the employee has not checked out till then s*he probably has forgotten it
        LocalTime autoOut = LocalTime.of(21,00);
        // Earliest time to start working possible
        LocalTime earliestStart = LocalTime.of(05, 00);
        // The time we have now (at the moment)
        LocalTime now = LocalTime.now();
        double max_workingTime_in_a_row = 5.5;
        double length_of_break = 0.25;
        double result;
        if (earliestStart.isAfter(inT)) {
            inT= earliestStart;
        }
        if (autoOut.equals(now) || autoOut.isBefore(now) || autoOut.isBefore(outT)){
            result = ChronoUnit.MINUTES.between(inT, autoOut);
            result = result / 60;

        } else {
            result = ChronoUnit.MINUTES.between(inT, outT);
            result = result / 60;
        }

        if (result >= max_workingTime_in_a_row) {
            result = result  - length_of_break ;
        }

        return result;
    }

    /**
     * Generate Weekly Report for individual Employee
     * @author Yomiyou
     * */
    @Secured("ROLE_ADMIN")
    @GetMapping("/weeklyReport/{id}")
    Optional<Work> userWeeklyReport(@PathVariable Long id){
        //Optional<Work> UserWork = repository.findByUser_ID(id);
        //  return UserWork;
        return null;
    }

    /**
     * Generate Monthly Report for individual Employee
     * @author Yomiyou
     * */
    @Secured("ROLE_ADMIN")
    @GetMapping("/monthlyReport/{id}")
    Optional<Work> userMonthlyReport(@PathVariable Long id){
        // Optional<Work> UserWork = repository.findByUser_ID(id);
        //return UserWork;
        return null;
    }

    /**
     * Get all the work from the logged in user
     * @author Clelia
     * */
    @ModelAttribute("allWork")
    @GetMapping("/allWork")
    List<Work> getAllWorkByCurrentUser(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        model.addAttribute("allWork", repository.findByUserId(userId));
        return repository.findByUserId(userId);
    }
    /**
     * Edit new Work Entity after Login
     * @author Yomiyou
     * TODO: new entry should only be automatically done by login and a separate method should update that entry when user loggs out.
     * */
   /* @ModelAttribute("work")
    @PostMapping ("/work")
    public Work addWorkSubmit(@RequestBody Work newWork, Model model) {
        model.addAttribute("work", newWork);
        /*for(Work work: newWork.getWorkList()){
            repository.save(work);
        }*/
    //return repository.save(newWork);
    //}

    @ModelAttribute("work")
    @PostMapping ("/work/{id}")
    public Work editWork(@RequestBody Work newWork, @PathVariable Long id, Model model) {
        model.addAttribute("work", newWork);
        Work work = repository.findById(id).get();
        work.setStart_at(newWork.getStart_at());
        work.setEnd_at(newWork.getEnd_at());
        return repository.save(newWork);
    }
    /**
     * Get information of the current work day
     *
     * @return*/
    @ModelAttribute("work")
    @GetMapping("/work")
    public List<Work> workForm(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        Date date = new Date(1621029600000L);
        model.addAttribute("work", repository.findByDateAndUserId(date,userId));
        return repository.findByDateAndUserId(date, userId);
    }





}
