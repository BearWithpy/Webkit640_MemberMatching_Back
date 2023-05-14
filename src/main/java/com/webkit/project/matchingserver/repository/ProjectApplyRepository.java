package com.webkit.project.matchingserver.repository;


import com.webkit.project.matchingserver.domain.ProjectApply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectApplyRepository extends JpaRepository<ProjectApply, String> {

    Boolean existsByMemberid(String member_id);

    Optional<ProjectApply> findByProjectid(String projectid);

    Optional<ProjectApply> findByMemberid(String memberid);

    Optional<ProjectApply> findByMemberidAndProjectid(String memberid, String projectid);

    List<ProjectApply> findAllByMemberid(String memberid);

    List<ProjectApply> findAllByProjectid(String id);



    void deleteByMemberidAndProjectid(String memberid, String projectid);
}
