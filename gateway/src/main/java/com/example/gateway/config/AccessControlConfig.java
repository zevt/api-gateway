package com.example.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class AccessControlConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**");
        //                .allowedMethods("OPTIONS", "GET", "POST", "HEAD", "TRACE", "PATCH")
        //                .allowCredentials(true)
//        .allowedOrigins(allowedOrigins.split("(\\s*)([,])(\\s*)"))
//        .allowedOrigins("**");
  }
}
