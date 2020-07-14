package com.example.projects.resources;

import com.example.projects.models.Clone;
import com.example.projects.models.Project;
import com.example.projects.repositories.ProjectRepository;
import com.example.projects.services.StreamGobbler;
import com.example.projects.services.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/clone")
@Api(value = "Cloner Management System")
public class CloneResource {

    @Autowired
    private ProjectRepository repository;

    @ApiOperation(value = "Clone a specific project", response = List.class)
    @GetMapping("/{idProject}")
    public ResponseEntity<?> clone(@PathVariable Long idProject){
        try {
            Optional<Project> project = repository.findById(idProject);
            if(project.isPresent()){
                if(project.get().get_public()){

                    Clone clone = cloneRepository(project.get());
                    return new ResponseEntity<>(clone, HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("No permission", HttpStatus.UNAUTHORIZED);
                }
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Clone cloneRepository(Project project){
        Clone clone = new Clone();
        clone.setName(project.getName());
        clone.setUrl(project.getUrl());

        File directory = new File("/var/projects");
        if (!directory.exists()){
            directory.mkdir();
        }
        File repo = new File("/var/projects/"+project.getName()+"-"+project.get_id());
        if(repo.exists()){
            deleteDirectory(repo);
            Tools.log(1, "Removing current repository");
        }
        try{
            Tools.log(1, "Cloning : "+project.getUrl());
            Git.cloneRepository().setURI(project.getUrl()).setDirectory(repo).call();
            clone.setBranch(Git.open(repo).getRepository().getBranch());
            clone.setPath(repo.getPath());
            return clone;
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
