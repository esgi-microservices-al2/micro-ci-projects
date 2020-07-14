package com.example.projects.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class HealthCheckerResource {

    @RequestMapping("/health")
    public String healthChecker(){
        return "Projects is running";
    }
}
