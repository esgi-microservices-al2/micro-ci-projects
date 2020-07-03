package com.example.projects.resources;

import com.example.projects.DTO.ProjectDTO;
import com.example.projects.exceptions.ProjectNotFoundException;
import com.example.projects.models.Project;
import com.example.projects.repositories.ProjectRepository;
import com.example.projects.services.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
@Api(value = "Project Management System")
public class ProjectResource {


    @Autowired
    private final  ProjectRepository repository;

    private final ProjectService projectService;

    @ApiOperation(value = "Get a specific project", response = List.class)
    @GetMapping("/{idProject}")
    public ResponseEntity<?> getProject(@PathVariable Long idProject){
        try {
            Optional<Project> project = repository.findById(idProject);
            if(project.isPresent()){
                return new ResponseEntity<>(project, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Project id not found : "+ idProject, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Get a list of projects", response = List.class)
    @GetMapping("/projects")
    public ResponseEntity<?> getListOfProjects(){
        List<Project> projects = repository.findAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a project", response = List.class)
    @PostMapping("/")
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO project){
        Project createdProject = projectService.createProject(project);
        repository.save(createdProject);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }


    //@ApiOperation(value = "Update a specific project", response = List.class)
    @PutMapping("/{idProject}")
    public ResponseEntity<?> updateProject(@PathVariable Long idProject, @RequestBody Project projectUpdate){
        try {
            Project updatedProject = repository.findById(idProject).orElseThrow(() -> new ProjectNotFoundException(idProject));
            updatedProject.setName(projectUpdate.getName());
            updatedProject.setOwner(projectUpdate.getOwner());
            updatedProject.setRunner(projectUpdate.getRunner());
            updatedProject.setUrl(projectUpdate.getUrl());
            repository.save(updatedProject);

            return new ResponseEntity<>("Project updated : " + updatedProject, HttpStatus.OK);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Project id not found : " + idProject, HttpStatus.OK);
        }
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
