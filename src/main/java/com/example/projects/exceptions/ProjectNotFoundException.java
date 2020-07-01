package com.example.projects.exceptions;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(Long id){

        super("Could not find player " + id);
    }
}
