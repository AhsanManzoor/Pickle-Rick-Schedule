package com.picklerick.schedule.rest.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewConfig implements WebMvcConfigurer {

    /**
     * Add valid views to the view controller
     * It specifies which view to render for which URL
     *
     * @author Clelia
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/forgot").setViewName("forgotPassword");
        registry.addViewController("/changePassword").setViewName("ChangePassword");
        registry.addViewController("/user").setViewName("addNewUser");
        registry.addViewController("/overview").setViewName("overview");
        registry.addViewController("/schedule").setViewName("schedule");
        registry.addViewController("/settings").setViewName("settings");
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/work").setViewName("work");
        registry.addViewController("/allWork").setViewName("allWork");
        registry.addViewController("/dailyWork").setViewName("dailyWork");
        registry.addViewController("/weeklyWork").setViewName("weeklyWork");
        registry.addViewController("/monthlyWork").setViewName("monthlyWork");
        registry.addViewController("/overviewAdmin").setViewName("overviewAdmin");
        registry.addViewController("/allWorkAdmin").setViewName("allWorkAdmin");
        registry.addViewController("/dailyWorkAdmin").setViewName("dailyWorkAdmin");
        registry.addViewController("/weeklyWorkAdmin").setViewName("weeklyWorkAdmin");
        registry.addViewController("/monthlyWorkAdmin").setViewName("monthlyWorkAdmin");
    }
}
