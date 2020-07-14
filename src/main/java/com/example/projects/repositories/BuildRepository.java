package com.example.projects.repositories;


import com.example.projects.models.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildRepository extends JpaRepository<Build, Long> {
    List<Build> findByIdProject(Long idProject);
}
