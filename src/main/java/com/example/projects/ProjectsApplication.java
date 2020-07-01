package com.example.projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.consul.*;


@SpringBootApplication
@Configuration
public class ProjectsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectsApplication.class, args);
    }
}
