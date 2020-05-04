package com.example.projects.resources;

import com.example.projects.DTO.ProjectDTO;
import com.example.projects.models.Project;
import com.example.projects.repositories.ProjectRepository;
import com.example.projects.services.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@Api(value = "Project Management System")
public class ProjectResource {


    @Autowired
    ProjectRepository repository;

    private final ProjectService projectService;

    public ProjectResource(ProjectService projectService){
        this.projectService = projectService;
    }

    @ApiOperation(value = "Get a specific project", response = List.class)
    @GetMapping("/{idProject}")
    public ResponseEntity<?> getProject(@PathVariable String idProject){
        return new ResponseEntity<>("SHOULD GET A PROJECT", HttpStatus.OK);
    }

    @ApiOperation(value = "Get a list of projects", response = List.class)
    @GetMapping("/projects")
    public ResponseEntity<?> getListOfProjects(){
        return new ResponseEntity<>("SHOULD GET A LIST OF PROJECT", HttpStatus.OK);
    }

    @ApiOperation(value = "Create a project", response = List.class)
    @PostMapping("/")
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO project){
        Project createdProject = projectService.createProject(project);
        repository.save(createdProject);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update a specific project", response = List.class)
    @PutMapping("/{idProject}")
    public ResponseEntity<?> updateProject(@PathVariable String idProject){
        return new ResponseEntity<>("SHOULD UPDATE A PROJECT", HttpStatus.OK);
    }


    @ApiOperation(value = "Delete a specific project", response = List.class)
    @DeleteMapping("/{idProject}")
    public ResponseEntity<?> deleteProject(@PathVariable Long idProject){
        try{
            repository.deleteById(idProject);
            System.out.println("Project deleted with id : "+idProject);
            return new ResponseEntity<>("Project deleted with id : "+idProject, HttpStatus.OK);
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("No project found for the id : "+idProject, HttpStatus.OK);
        }

    }
}
