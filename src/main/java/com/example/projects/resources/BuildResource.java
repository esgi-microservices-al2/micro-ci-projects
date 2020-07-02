package com.example.projects.resources;


import com.example.projects.DTO.ProjectDTO;
import com.example.projects.models.Project;
import com.example.projects.services.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/builds")
@Api(value = "Cloner Management System")
public class BuildResource {

    @ApiOperation(value = "Build a project", response = List.class)
    @PostMapping("/{idProject}")
    public ResponseEntity<?> buildProject(@PathVariable Long idProject){
        if(idProject == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else{
            Tools.log(1, "Build request received for project : "+idProject);
            // TODO : should send build request to command micro-ci
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
