package com.example.projects.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@ApiModel(description = "All details about a Project.")
public class ProjectDTO {
    @ApiModelProperty(notes = "The database generated project ID")
    private Long _id;

    @ApiModelProperty(notes = "Project name")
    private String name;

    @ApiModelProperty(notes = "The URL generated by the system")
    private String url;

    @ApiModelProperty(notes = "The access right of the repository")
    private Boolean _public;

    @ApiModelProperty(notes = "The ID of the generated runner for the project CI")
    private String runner;

    @ApiModelProperty(notes = "The ID of the owner")
    private String owner;
}
