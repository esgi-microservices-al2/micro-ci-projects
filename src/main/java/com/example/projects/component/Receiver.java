package com.example.projects.component;

import com.example.projects.models.Build;
import com.example.projects.models.Project;
import com.example.projects.repositories.BuildRepository;
import com.example.projects.repositories.ProjectRepository;
import com.example.projects.resources.BuildResource;
import com.example.projects.services.Tools;
import org.apache.coyote.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BuildResource buildResource;

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = "al2.payload.q")
    public void receiveMessage(String message) {
        try{
            Tools.log(1, "Receiving a new message");
            JSONParser parser = new JSONParser();
            JSONObject input = (JSONObject) parser.parse(message);
            JSONObject repo = (JSONObject) input.get("repository");
            JSONObject user = (JSONObject) input.get("user");
            Project exists = projectRepository.findByUrl(repo.get("clone_url").toString());
            if(exists != null && exists.getUrl() != null){
                ResponseEntity<String>  response = buildResource.generateBuild(exists);
                if(response.getStatusCodeValue() == 200){
                    Tools.log(1, "Build generated for project : "+exists.get_id());
                }else{
                    Tools.log(1, "Build creation failed for project : "+exists.get_id());
                }
            }else{
                Project created = new Project();
                created.set_public(true);
                created.setOwner(user.get("username").toString());
                created.setUrl(repo.get("clone_url").toString());
                created.setName(repo.get("name").toString());
                created.setDefaultBranch(repo.get("default_branch").toString());
                projectRepository.save(created);
                ResponseEntity<String>  response = buildResource.generateBuild(created);
                if(response.getStatusCodeValue() == 200){
                    Tools.log(1, "Build generated for project : "+created.get_id());
                }else{
                    Tools.log(2, "Build creation failed for project : "+created.get_id());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(0);
        }
        latch.countDown();
    }

    @RabbitListener(queues = "al2.scheduler")
    public void receiveBuild(String message) {
        Long id = Long.parseLong(message);
        Optional<Project> exists = projectRepository.findById(id);
        if(!exists.isPresent()){
            Tools.log(2, "Project id : "+id+" does not exist");
        }else{
            ResponseEntity<String> response = buildResource.generateBuild(exists.get());
            if(response.getStatusCodeValue() == 200){
                Tools.log(1, "Build generated for project : "+id);
            }else{
                Tools.log(2, "Build creation failed for project : "+id);
            }
        }
    }
}
