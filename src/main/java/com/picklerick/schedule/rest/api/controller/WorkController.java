package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.model.*;
import com.picklerick.schedule.rest.api.repository.*;
import com.picklerick.schedule.rest.api.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


@RestController
@RequestMapping
public class WorkController {
    private final WorkRepository workRepository;
    private final UserRepository userRepository;
    private final WorkingDayRepository workingDayRepository;
    private final WorkingMonthRepository workingMonthRepository;
    private final WorkingWeekRepository workingWeekRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkController.class);

    /**
     * WorkController constructor initializing work repository
     *
     * @author Clelia
     * */
    public WorkController(WorkRepository workRepository, UserRepository userRepository, WorkingDayRepository workingDayRepository, WorkingMonthRepository workingMonthRepository, WorkingWeekRepository workingWeekRepository) {
        this.workRepository = workRepository;
        this.userRepository = userRepository;
        this.workingDayRepository = workingDayRepository;
        this.workingMonthRepository = workingMonthRepository;
        this.workingWeekRepository = workingWeekRepository;
    }

    /**
     * Once the user logs in it saves the time
     *
     * @param authentication
     * @author Clelia
     * */
    public void startTimeRecording(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        Work work = new Work(LocalDate.now(), LocalTime.now(), null, 0.0,  userId, false);
        workRepository.save(work);
    }

    /**
     * Once the user logged out it saves the time and calls the method for calculating the time
     *
     * @param authentication
     * @author Stefan
     * */
    public void endTimeRecording(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        ArrayList<Work> allWorkOfDay = (ArrayList<Work>) workRepository.findByDateAndUserId(LocalDate.now(), userId);

        // Go through all the work entries of the day and find the one which does not have
        // an end_at value, fill it and save it to the database
        for(Work workEntry : allWorkOfDay) {
            if(workEntry.getEnd_at() == null) {
                workEntry.setEndAt(LocalTime.now());
                workEntry.setWorkedTime(workingTimeCalculator(workEntry.getStart_at(), workEntry.getEnd_at()));
                workRepository.save(workEntry);
            }
        }

        calculateWorkingTimeForOneDay(userId, LocalDate.now());
        calculateWorkingTimeForCurrentMonth(userId);
        calculateWorkingTimeForCurrentWeek(userId);

    }

    /**
     * Check if user already has a work summary of the day
     * if not, create a new workDay entry
     *
     * @author Stefan
     * @param userId of newly logged out user
     * */
    public void checkWorkDayEntry(Long userId) {
        if (workingDayRepository.findByDateAndUserId(LocalDate.now(), userId) == null){
            User user = userRepository.findById(userId).get();
            WorkingDay workingDay = new WorkingDay();
            workingDay.setDate(LocalDate.now());
            workingDay.setUserId(userId);
            workingDay.setScheduledTime(user.getWeeklySchedule());
            workingDay.setWorkedTime(0.0);
            workingDayRepository.save(workingDay);
        }
    }

    /**
     * Check if user already has a work summary of the week
     * if not, create a new workDay entry
     *
     * @author Stefan
     * @param monday
     * @param userId of newly logged out user
     * */
    public void checkWorkWeekEntry(LocalDate monday, Long userId) {
        if(workingWeekRepository.findByStartDateAndUserId(monday, userId) == null) {
            Optional<User> user = userRepository.findById(userId);
            WorkingWeek workingWeek = new WorkingWeek();
            workingWeek.setStartDate(monday);
            workingWeek.setUser_id(userId);
            Double time = user.get().getWeeklySchedule();
            workingWeek.setScheduledTime(time);
            workingWeek.setWorkedTime(0.0);
            workingWeekRepository.save(workingWeek);
        }
    }

    /**
     * Check if user already has a work summary of the month
     * if not, create a new workDay entry
     *
     * @author Stefan
     * @param start
     * @param end
     * @param userId of newly logged out user
     * */
    public void checkWorkMonthEntry(LocalDate start, LocalDate end, Long userId) {
        if(workingMonthRepository.findByStartDateAndUserId(start, userId) == null) {
            Optional<User> user = userRepository.findById(userId);
            WorkingMonth workingMonth = new WorkingMonth();
            workingMonth.setStartDate(start);
            workingMonth.setUser_id(userId);
            workingMonthRepository.save(workingMonth);
            workingMonth.setEndDate(end);
            Double time = user.get().getWeeklySchedule();
            workingMonth.setScheduledTime(time);
            workingMonth.setWorkedTime(0.0);
            workingMonthRepository.save(workingMonth);
        }
    }


    /**
     * Calculate working time (between check-in and check-out)
     * @param inT check-in, outT check-out
     * @author Stefan
     * */
    public static Double workingTimeCalculator(LocalTime inT, LocalTime outT) {
        // Transforming to LocalDate for method internal processing
        // In documentation defined end time, if the employee has not checked out till then s*he probably has forgotten it
        LocalTime autoOut = LocalTime.of(21,00);
        // Earliest time to start working possible
        LocalTime earliestStart = LocalTime.of(05, 00);
        // The time we have now (at the moment)
        LocalTime now = LocalTime.now();
        double max_workingTime_in_a_row = 5.5;
        double length_of_break = 0.25;
        double result;
        if (inT.isAfter(outT)|| inT.isAfter(autoOut)){
            return 0.0;
        }
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
        if(result > 15.25){
            return 0.0;
        }

        return result;
    }

    /**
     * Here we calculate the total working time of a user in a day
     *
     * @param userId, date
     * @author Stefan
     * */
    public void calculateWorkingTimeForOneDay (Long userId, LocalDate date) {
        checkWorkDayEntry(userId);
        List<Work> allWorkOfDay = workRepository.findByDateAndUserId(LocalDate.now(), userId);
        WorkingDay workDayEntry = workingDayRepository.findByDateAndUserId(date, userId);

        for(Work workEntry : allWorkOfDay){
            if(workEntry.getProceeded() == false) {
                workDayEntry.setWorkedTime(workDayEntry.getWorkedTime() + workEntry.getWorkedTime()); // TODO I do not understand this line
                workEntry.setProceeded(true);
                workRepository.save(workEntry);
                workingDayRepository.save(workDayEntry);
            }
        }
    }

    /**
     * Here we calculate the total working time of a user of the CURRENT week
     *
     * @param userId
     * @author Stefan
     * */
    public void calculateWorkingTimeForCurrentWeek (Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today;
        Double totalWorkedThisWeek = 0.0;
        List<WorkingDay> daysOfThisWeek = new ArrayList<>();
        List<WorkingDay> allWorkingDays = workingDayRepository.findByUserId(userId);

        // Code from:
        // http://www.java2s.com/Tutorials/Java/Data_Type_How_to/Date/Get_current_week_start_and_end_date_MONDAY_TO_SUNDAY_.htm
        // Go backward to get Monday
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        // check if working week already exists if not create
        checkWorkWeekEntry(monday,userId);

        for(WorkingDay day: allWorkingDays) {
            if (!day.getDate().isBefore(monday) && !day.getDate().isAfter(monday.plusDays(6)) && day.getProceededWeek() == false) {
                daysOfThisWeek.add(day);
            }
        }

        for (WorkingDay day: daysOfThisWeek) {
            if (day.getProceededWeek() == false) {
                totalWorkedThisWeek = totalWorkedThisWeek + day.getWorkedTime();
                day.setProceededWeek(true);
                workingDayRepository.save(day);
            }
        }

        WorkingWeek workingWeek = workingWeekRepository.findByStartDateAndUserId(monday, userId);
        workingWeek.setWorkedTime( workingWeek.getWorkedTime() + totalWorkedThisWeek);
        workingWeekRepository.save(workingWeek);
    }

    /**
     * Here we calculate the total working time of a user of CURRENT month
     *
     * @param userId
     * @author Stefan
     * */
    public void calculateWorkingTimeForCurrentMonth (Long userId) {
        LocalDate start = LocalDate.now().minusDays(LocalDate.now().getDayOfMonth() + 1);
        LocalDate end = LocalDate.now().minusDays(LocalDate.now().getDayOfMonth()).plusMonths(1);
        Double totalWorkedThisMonth = 0.0;

        checkWorkMonthEntry(start, end, userId);

        for (int i = 0; i < LocalDate.now().getDayOfMonth(); i++) {
            if (workingDayRepository.findByDateAndUserId(start.plusDays(i), userId) != null
                    && workingDayRepository.findByDateAndUserId(start.plusDays(i), userId).getProceededMonth() == false) {
                WorkingDay day = workingDayRepository.findByDateAndUserId(start.plusDays(i), userId);
                totalWorkedThisMonth = totalWorkedThisMonth + day.getWorkedTime();
                day.setProceededMonth(true);
            }
        }
        checkWorkMonthEntry(start, end, userId);
        WorkingMonth workingMonth = workingMonthRepository.findByStartDateAndUserId(start, userId);
        workingMonth.setWorkedTime(workingMonth.getWorkedTime() + totalWorkedThisMonth);
        workingMonthRepository.save(workingMonth);
    }

    /**
     * Here we calculate the total working time of a user of the CURRENT year
     *
     * @param userId
     * @author Stefan
     * TODO @Stefan, ? is it used on the page? if not please remove
     * */
    public Double calculateWorkingTimeForCurrentYear (Long userId) {
        LocalDate start = LocalDate.now().minusDays(LocalDate.now().getDayOfYear()-1);
        LocalDate today = LocalDate.now();
        double result = 0.0;

        for(int i = 0; i < today.getDayOfYear(); i++){
            // result = result + calculateWorkingTimeForOneDay(userId, start.plusDays(i));
        }
        return result;
    }

    /**
     * Here we calculate the total working time of all employees that have the same manager
     *
     * @param managerId
     * @author Stefan
     * */
    @Secured("ROLE_ADMIN") // @ Author: Clelia
    public ArrayList<WorkingDay> calculateWorkingTimeForOneDayAsAdmin (Long managerId, LocalDate date) {
        List<WorkingDay> days = (List<WorkingDay>) workingDayRepository.findAll();
        ArrayList<WorkingDay> finalDays = new ArrayList<>();
        for (int i = 0; i < days.size(); i++){
            if (userRepository.findById(days.get(i).getUserId()).get().getManagerId() == managerId){
                finalDays.add(days.get(i));
            }
        }
        return finalDays;
    }

    /**
     * Here we calculate the total working time of all employees that have the same manager for the CURRENT month
     *
     * @param managerId
     * @author Stefan
     * */
    @Secured("ROLE_ADMIN") // @ Author: Clelia
    public ArrayList<WorkingMonth> calculateWorkingTimeForCurrentMonthAsAdmin (Long managerId) {
        List<WorkingMonth> months = (List<WorkingMonth>) workingMonthRepository.findAll();
        ArrayList<WorkingMonth> finalMonths = new ArrayList<>();
        for (int i = 0; i < months.size(); i++){
            if (userRepository.findById(months.get(i).getUser_id()).get().getManagerId() == managerId){
                finalMonths.add(months.get(i));
            }
        }
        return finalMonths;
    }

    /**
     * Here we calculate the total working time of all employees that have the same manager for the CURRENT Year
     *
     * @param managerId
     * @author Stefan
     * TODO: @stefan, if not needed please remove
     */
    @Secured("ROLE_ADMIN") // @ Author: Clelia
    public ArrayList<Double[]> calculateWorkingTimeForCurrentYearAsAdmin (Long managerId) {
        List<User> Users = userRepository.findByManagerId(managerId);
        ArrayList<Double[]> result = new ArrayList<>();

        for (int i = 0; i < Users.size(); i++){
            Double[] n = new Double[2];
            Double workedTime = calculateWorkingTimeForCurrentYear(Users.get(i).getId());
            Double userID = Double.valueOf(Users.get(i).getId().toString());
            n[0] = userID; n[1] = workedTime;
            result.add(n);
            n[0] = null; n[1] = null;
        }
        return result;
    }

    /**
     * Here we calculate the total working time of all employees that have the same manager for the CURRENT Week
     *
     * @param managerId
     * @author Stefan
     * */
    @Secured("ROLE_ADMIN") // @ Author: Clelia
    public ArrayList<WorkingWeek> calculateWorkingTimeForCurrentWeekAsAdmin (Long managerId) {
        List<WorkingWeek> weeks = (List<WorkingWeek>) workingWeekRepository.findAll();
        ArrayList<WorkingWeek> finalWeeks = new ArrayList<>();
        for (int i = 0; i < weeks.size(); i++){
            if (userRepository.findById(weeks.get(i).getUser_id()).get().getManagerId() == managerId){
                finalWeeks.add(weeks.get(i));
            }
        }
        return finalWeeks;
    }

    /**
     * Get all the work from the logged in user
     *
     * @author Clelia
     * */
    @ModelAttribute("allWork")
    @GetMapping("/allWork")
    List<Work> getAllWorkByCurrentUser(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        model.addAttribute("allWork", workRepository.findByUserId(userId));
        return workRepository.findByUserId(userId);
    }

    /**
     * Edit Work a specific work entry of id work_id
     *
     * @author Clelia
     * */
    @PostMapping ("/work/{id}")
    public Work editWork(@ModelAttribute Work updatedWork, @PathVariable Long id, Model model) {
        model.addAttribute("work", updatedWork);
        Work oldWork = workRepository.findById(id).get();
        LOGGER.info("Work was updated");
        return workRepository.findById(id)
                .map(w -> {
                    w.setStart_at(updatedWork.getStart_at());
                    w.setEndAt(updatedWork.getEnd_at());
                    return workRepository.save(w);
                }).orElseGet(()-> workRepository.save(oldWork));
    }

    /**
     * Get information of the current work day
     *
     * @author Clelia
     * */
    @ModelAttribute("work")
    @GetMapping("/work")
    public List<Work> workForm(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        model.addAttribute("work", workRepository.findByDateAndUserId(LocalDate.now(),userId));
        return workRepository.findByDateAndUserId(LocalDate.now(), userId);
    }

    /**
     * Get information of the daily contribution
     *
     * @author Stefan
     * */
    @ModelAttribute("dailyWork")
    @GetMapping("/dailyWork")
    List<WorkingDay> getDailyWorkByCurrentUser(Authentication authentication, Model model) {
        //      List<WorkingDay> getWorkedTime(Authentication authentication, Model model){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        model.addAttribute("dailyWork", workingDayRepository.findByUserId(userId));

        return workingDayRepository.findByUserId(userId);
    }

    /**
     * Get information of the weekly contribution
     *
     * @author Stefan
     * */
    @ModelAttribute("weeklyWork")
    @GetMapping("/weeklyWork")
    List<WorkingWeek> getWeeklyWorkByCurrentUser(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        model.addAttribute("weeklyWork", workingWeekRepository.findByUserId(userId));
        return workingWeekRepository.findByUserId(userId);
    }

    /**
     * Get information of the monthly contribution
     *
     * @author Stefan
     * */
    @ModelAttribute("monthlyWork")
    @GetMapping("/monthlyWork")
    List<WorkingMonth> getMonthlyWorkByCurrentUser(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        model.addAttribute("monthlyWork", workingMonthRepository.findByUserId(userId));
        return workingMonthRepository.findByUserId(userId);
    }

    /**
     * Get information about all the work entries of users created by the admin
     *
     * @author Stefan
     * */
  /*  @ModelAttribute("allWorkAdmin")
    @Secured("ROLE_ADMIN")
    @GetMapping("/allWorkAdmin")
    List<Work> getWorkOfEmployeesAsAdmin(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long managerId = userDetails.getUserId();
        ArrayList<Work> finalDays = new ArrayList<>();
      /*  ArrayList<Work> allDays = (ArrayList<Work>) workRepository.findAll();


            for (int i = 0; i < allDays.size(); i++) {
                    User user = userRepository.findById(allDays.get(i).getUser_id()).get();
                    if (user.getManagerId() == managerId) {
                        finalDays.add(allDays.get(i));
                    }
                }
        if (finalDays.isEmpty()) {
            Work n = new Work();
            finalDays.add(n);
        }
        model.addAttribute("user", userRepository.findById(userDetails.getUserId()).get());
        model.addAttribute("allWorkAdmin", finalDays);
        return finalDays; */
       /* List<User> users = userRepository.findByManagerId(managerId);
        for (User user: users) {
            List<Work> works = workRepository.findByUserId(user.getId());
            ArrayList<LocalDate> dates = new ArrayList<>();
            for (Work work: works) {
                dates.add(work.getDate());
            }
             Optional<LocalDate> maxday = dates.stream().max(LocalDate::compareTo);
        }
    }
*/
    /**
     * Get information about the daily contribution of users created by the logged in admin
     *
     * @author Stefan
     * */
    @ModelAttribute("dailyWorkAdmin")
    @Secured("ROLE_ADMIN")
    @GetMapping("/dailyWorkAdmin")
    List<WorkingDay> getWorkingDaysOfEmployeesAsAdmin(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long managerId = userDetails.getUserId();
        ArrayList<WorkingDay> allDays = (ArrayList<WorkingDay>) workingDayRepository.findAll();
        ArrayList<WorkingDay> finalDays = new ArrayList<>();

        for (int i = 0; i < allDays.size(); i++) {
            Long idOfManager = userRepository.findById(allDays.get(i).getUserId()).get().getManagerId();
            if (idOfManager == managerId) {
                finalDays.add(allDays.get(i));

            }
        }
        if (finalDays.isEmpty()) {
            WorkingDay n = new WorkingDay();
            finalDays.add(n);
        }

        model.addAttribute("dailyWorkAdmin", finalDays);
        return finalDays;
    }

    /**
     * Get information about the weekly contribution of users created by the logged in admin
     *
     * @author Stefan
     * */
    @ModelAttribute("weeklyWorkAdmin")
    @Secured("ROLE_ADMIN")
    @GetMapping("/weeklyWorkAdmin")
    List<WorkingWeek> getWorkingWeeksOfEmployeesAsAdmin(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long managerId = userDetails.getUserId();
        ArrayList<WorkingWeek> allWeeks = (ArrayList<WorkingWeek>) workingWeekRepository.findAll();
        ArrayList<WorkingWeek> finalWeeks = new ArrayList<>();

        for (int i = 0; i < allWeeks.size(); i++) {
            Long idOfManager = userRepository.findById(allWeeks.get(i).getUser_id()).get().getManagerId();
            if (idOfManager == managerId) {
                finalWeeks.add(allWeeks.get(i));
            }
        }
        if (finalWeeks.isEmpty()) {
            WorkingWeek n = new WorkingWeek();
            finalWeeks.add(n);
        }
        model.addAttribute("weeklyWorkAdmin", finalWeeks);
        return finalWeeks;
    }

    /**
     * Get information about the monthly contribution of users created by the logged in admin
     *
     * @author Stefan
     * */
    @ModelAttribute("monthlyWorkAdmin")
    @Secured("ROLE_ADMIN")
    @GetMapping("/monthlyWorkAdmin")
    List<WorkingMonth> getWorkingMonthsOfEmployeesAsAdmin(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long managerId = userDetails.getUserId();
        ArrayList<WorkingMonth> allMonths = (ArrayList<WorkingMonth>) workingMonthRepository.findAll();
        ArrayList<WorkingMonth> finalMonth = new ArrayList<>();

        for (int i = 0; i < allMonths.size(); i++) {
            if (userRepository.findById(allMonths.get(i).getUser_id()).get().getManagerId() != null) {
                Long idOfManager = userRepository.findById(allMonths.get(i).getUser_id()).get().getManagerId();
                if (idOfManager == managerId) {
                    finalMonth.add(allMonths.get(i));
                }
            }
        }

        model.addAttribute("monthlyWorkAdmin", finalMonth);
        return finalMonth;
    }
}
