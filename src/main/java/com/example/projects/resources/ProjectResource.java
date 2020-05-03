package com.example.projects.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectResource {

    @GetMapping("/{idProject}")
    public ResponseEntity<?> getProject(@PathVariable String idProject){
        return new ResponseEntity<>("SHOULD GET A PROJECT", HttpStatus.OK);
    }

    @GetMapping("/projects")
    public ResponseEntity<?> getListOfProjects(){
        return new ResponseEntity<>("SHOULD GET A LIST OF PROJECT", HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createProject(){
        return new ResponseEntity<>("SHOULD CREATE A PROJECT", HttpStatus.OK);
    }

    @PutMapping("/{idProject}")
    public ResponseEntity<?> updateProject(@PathVariable String idProject){
        return new ResponseEntity<>("SHOULD UPDATE A PROJECT", HttpStatus.OK);
    }

    @DeleteMapping("/{idProject}")
    public ResponseEntity<?> deleteProject(@PathVariable String idProject){
        return new ResponseEntity<>("SHOULD DELETE A PROJECT", HttpStatus.OK);
    }
}
