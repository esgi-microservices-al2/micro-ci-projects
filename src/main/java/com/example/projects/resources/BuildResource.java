package com.example.projects.resources;


import com.example.projects.DTO.ProjectDTO;
import com.example.projects.models.Build;
import com.example.projects.models.Clone;
import com.example.projects.models.Project;
import com.example.projects.repositories.BuildRepository;
import com.example.projects.repositories.ProjectRepository;
import com.example.projects.services.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.coyote.Response;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/builds")
@Api(value = "Cloner Management System")
public class BuildResource {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private CloneResource cloneResource;

    @ApiOperation(value = "Build a project", response = List.class)
    @PostMapping("/{idProject}")
    public ResponseEntity<?> buildProject(@PathVariable Long idProject){
        Optional<Project> exists = projectRepository.findById(idProject);
        if(!exists.isPresent()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else{
            ResponseEntity<String> response = generateBuild(exists.get());
            if(response.getStatusCodeValue() == 200){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
    }

    @ApiOperation(value = "Get list of builds for a project", response = List.class)
    @GetMapping("/{idProject}")
    public ResponseEntity<?> getBuilds(@PathVariable Long idProject){
        List<Build> list = buildRepository.findByIdProject(idProject);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<String> generateBuild(Project project){
        Tools.log(1, "Build request received for project : "+project.get_id());
        Clone cloned = cloneResource.cloneRepository(project);
        Build build = new Build();
        build.setIdProject(project.get_id());
        build.setCreatedAt(new Date());
        buildRepository.save(build);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject params = new JSONObject();
        params.put("projectId", project.get_id());
        params.put("buildId", build.get_id());
        params.put("folder", cloned.getPath());
        HttpEntity<String> request = new HttpEntity<String>(params.toString(), headers);
        return restTemplate.postForEntity("http://micro-ci-al2.internal.cloudapp.net:9100rpc/"+project.get_id()+"/execute", request, String.class);
    }
}
