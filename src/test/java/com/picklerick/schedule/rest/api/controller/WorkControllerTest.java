package com.picklerick.schedule.rest.api.controller;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Time;
import java.time.LocalTime;


@SpringBootTest
public class WorkControllerTest {

    /**
     * @author Stefan
     */
     @Test
    void workingTimeCalculatorTest() {

        /**
         * Basically, there are the following cases we should and will test:
         *
         * (1)  We need to check whether the time is correctly calculated if non of the cases below is considered
         * (2)  Employee check's in before the earliest possible start
         * (3)  Employee check's not out before the latest possible checkout
         * (4)  Employee has forgotten to check out for a break or worked longer than the
         *      maximally allowed period in a row
         */

        /**
         * (1)  Regular calculations
         */

        LocalTime in1 = LocalTime.of(5,01);
        LocalTime out1 = LocalTime.of(6,01);
        double expSol1 = 1.0;
        assertThat(WorkController.workingTimeCalculator(in1, out1)).isEqualTo(expSol1);

        LocalTime in2 = LocalTime.of(7,0);
        LocalTime out2 = LocalTime.of(10,15);
        double expSol2 = (3.25);
        assertThat(WorkController.workingTimeCalculator(in2, out2)).isEqualTo(expSol2);

      /**
         * (2)  Employee check's in to early
         */

        LocalTime in3 = LocalTime.of(4,0);
        LocalTime out3 = LocalTime.of(6,0);
        double expSol3 = 1.0;
        assertThat(WorkController.workingTimeCalculator(in3, out3)).isEqualTo(expSol3);

        LocalTime in4 = LocalTime.of(4,59);
        LocalTime out4 = LocalTime.of(6,0);
        double expSol4 = 1.0;
        assertThat(WorkController.workingTimeCalculator(in4, out4)).isEqualTo(expSol4);

        /**
         * (3)  Employee check's out to late
         */

        LocalTime in5 = LocalTime.of(20,0);
        LocalTime out5 = LocalTime.of(22,0);
        double expSol5 = 1.0;
        assertThat(WorkController.workingTimeCalculator(in5, out5)).isEqualTo(expSol5);

        /**
         * (4)  Break got forgotten
         */

        LocalTime in6 = LocalTime.of(6,0);
        LocalTime out6 = LocalTime.of(11,30);
        double expSol6 = 5.25;
        assertThat(WorkController.workingTimeCalculator(in6, out6)).isEqualTo(expSol6);

        LocalTime in7 = LocalTime.of(10,0);
        LocalTime out7 = LocalTime.of(16,0);
        double expSol7 = 5.75;
        assertThat(WorkController.workingTimeCalculator(in7, out7)).isEqualTo(expSol7);


    }

}


