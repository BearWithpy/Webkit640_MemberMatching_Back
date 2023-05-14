package com.webkit.project.matchingserver.repository;


import com.webkit.project.matchingserver.domain.ProjectJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectJoinRepository extends JpaRepository<ProjectJoin, String> {
    List<ProjectJoin> findAllByMemberid(String id);

    boolean existsByProjectid(String id);
}
