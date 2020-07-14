package com.example.projects.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "builds")
@ApiModel(description = "Build table for a project.")
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated build ID")
    private Long _id;

    @Column(name= "id_project")
    private Long idProject;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
}
