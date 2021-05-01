package com.picklerick.schedule.rest.api.controller;

import com.picklerick.schedule.rest.api.repository.WorkRepository;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


@RestController
public class WorkController {
    private final WorkRepository repository;

    /**
     * WorkController constructer initializing work repository
     * @author Clelia
     * */
    public WorkController(WorkRepository repository) {
        this.repository = repository;
    }

    /**
     * Calculate working time (between check-in and check-out)
     * @param in check-in, out check-out
     * @author Stefan
     */
    // I still need to do the test
    // Output type questionable
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

}
