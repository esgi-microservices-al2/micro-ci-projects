package com.example.projects.services;


import com.example.projects.DTO.ProjectDTO;
import com.example.projects.models.Project;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectService {


    public Project createProject(ProjectDTO projectDTO){
        Project project = new Project();
        project.setUrl("microci-projects@"+projectDTO.getName());
        project.setOwner(projectDTO.getOwner());
        project.setSource(projectDTO.getSource());
        project.setName(projectDTO.getName());
        project.set_public(projectDTO.get_public());
        System.out.println("Created project with name : "+projectDTO.getName());
        return project;
    }
}
