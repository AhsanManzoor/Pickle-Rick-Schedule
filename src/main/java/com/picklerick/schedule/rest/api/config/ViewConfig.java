package com.picklerick.schedule.rest.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewConfig implements WebMvcConfigurer {

    /**
     * Add valid views to the view controller
     * It specifies which view to render for which URL
     * @author Clelia
     * */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/overview").setViewName("overview");
        registry.addViewController("/schedule").setViewName("schedule");
    }
}
