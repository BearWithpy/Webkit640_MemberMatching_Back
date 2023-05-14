package com.webkit.project.matchingserver.service;


import com.webkit.project.matchingserver.domain.MemberEntity;
import com.webkit.project.matchingserver.domain.ProjectPost;
import com.webkit.project.matchingserver.repository.MemberRepository;
import com.webkit.project.matchingserver.repository.ProjectPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectPostService {
    @Autowired
    ProjectPostRepository repository;

    @Autowired
    MemberRepository memberRepository;


    public ProjectPost createProject(final ProjectPost projectPost) {
        if (projectPost == null || projectPost.getOwner() == null) {
            throw new RuntimeException("Invalid arguments");
        }

//        final String id = projectPost.getId();
//        if (repository.existsById(id)) {
//            log.warn("Email already exists {}", id);
//            throw new RuntimeException("Email already exists");
//        }
        return repository.save(projectPost);
    }

    public List<ProjectPost> findAllProject() {
        List<ProjectPost> idList = repository.findAll();
        idList.forEach(project -> project.setOwner(memberRepository.findById(project.getOwner()).get().getName()));
        return idList;
    }


    public Optional<ProjectPost> findOne(final String id) {
        Optional<ProjectPost> idList = repository.findById(id);
        return idList;
    }

    public List<ProjectPost> retrieve(final String owner) {
        return repository.findByOwner(owner == null ? "" : owner);
    }

    public List<ProjectPost> updateProject(ProjectPost post) {

        final Optional<ProjectPost> original = repository.findById(post.getId());

        original.ifPresent(project -> {
            project.setTech(post.getTech());
            project.setPosition(post.getPosition());
            project.setTitle(post.getTitle());
            project.setHeadcount(post.getHeadcount());
            project.setContent(post.getContent());
            project.setStartdate(post.getStartdate());

            repository.save(project);
        });

        return retrieve(post.getOwner());
    }
}
