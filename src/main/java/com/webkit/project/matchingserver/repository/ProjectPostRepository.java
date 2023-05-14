package com.webkit.project.matchingserver.repository;


import com.webkit.project.matchingserver.domain.ProjectPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPostRepository extends JpaRepository<ProjectPost, String> {
    List<ProjectPost> findByOwner(String owner);



}
