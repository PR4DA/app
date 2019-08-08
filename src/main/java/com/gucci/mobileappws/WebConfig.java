package com.gucci.mobileappws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        //registry allows configure mappings, allow methods, header, and CROS
        registry.addMapping("/**").
                //by default all allowed
                allowedMethods("*").
                //not allowed br def
                allowedOrigins("*");
    }
}
