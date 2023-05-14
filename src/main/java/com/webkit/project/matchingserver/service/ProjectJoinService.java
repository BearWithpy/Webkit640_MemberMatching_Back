package com.webkit.project.matchingserver.service;


import com.webkit.project.matchingserver.domain.ProjectApply;
import com.webkit.project.matchingserver.domain.ProjectJoin;
import com.webkit.project.matchingserver.domain.ProjectPost;
import com.webkit.project.matchingserver.dto.ProjectJoinDTO;
import com.webkit.project.matchingserver.repository.MemberRepository;
import com.webkit.project.matchingserver.repository.ProjectJoinRepository;
import com.webkit.project.matchingserver.repository.ProjectPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectJoinService {

    @Autowired
    ProjectJoinRepository projectJoinRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProjectPostRepository projectPostRepository;

    public void saveProjectJoins(List<ProjectJoinDTO> projectJoinDTOs) {
        List<ProjectJoin> entities = new ArrayList<>();
        projectJoinDTOs.forEach(dto -> {
            dto.setMembername(memberRepository.findById(dto.getMemberid()).get().getName());
            dto.setProjectname(projectPostRepository.findById(dto.getProjectid()).get().getTitle());
            entities.add(dto.dtoToEntity());
        });
        projectJoinRepository.saveAll(entities);
    }


    public List<ProjectJoin> findAllMyJoinProject(String id) {
        List<ProjectJoin> idList = projectJoinRepository.findAllByMemberid(id);

        return idList;
    }


    public Boolean findProjectBeFinished(String id) {

        if (projectJoinRepository.existsByProjectid(id)) {
            log.warn("Join Project already exists {}", id);
            return true;
        }

        return false;
    }
}
