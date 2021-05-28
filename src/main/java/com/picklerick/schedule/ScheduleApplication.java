package com.picklerick.schedule;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleApplication.class, args);
	}

	//for coloring the console logs
	public static void setEnabled(AnsiOutput.Enabled enabled) {

	}

}
