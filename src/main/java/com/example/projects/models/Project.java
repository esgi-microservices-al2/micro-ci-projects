package com.example.projects.models;

import lombok.Data;

@Data
public class Project {
    private String _id;
    private String name;
    private String url;
    private Boolean _public;
    private String runner;
    private String owner;
    private String source;
}
