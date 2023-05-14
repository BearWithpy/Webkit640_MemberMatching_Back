package com.webkit.project.matchingserver.service;


import com.webkit.project.matchingserver.domain.ProjectApply;
import com.webkit.project.matchingserver.dto.ProjectApplyDTO;
import com.webkit.project.matchingserver.repository.MemberRepository;
import com.webkit.project.matchingserver.repository.ProjectApplyRepository;
import com.webkit.project.matchingserver.repository.ProjectPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectApplyService {

    @Autowired
    ProjectApplyRepository projectApplyRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProjectPostRepository projectPostRepository;


//    public ProjectApply createAppliance(ProjectApply newProject) {
//        if (newProject == null || newProject.getMemberid() == null) {
//            throw new RuntimeException("Invalid arguments");
//        }
////        Optional<ProjectApply> existingProject = projectApplyRepository.findByProjectid(newProject.getProjectid());
////        Optional<ProjectApply> existingMember = projectApplyRepository.findByMemberid(newProject.getMemberid());
////        if (existingProject.isPresent() && existingMember.isPresent()) {
////            throw new RuntimeException("Member already applied for this project");
////        }
//        return projectApplyRepository.save(newProject);
//    }

    public List<ProjectApply> findAllMyProject(String id) {
        List<ProjectApply> idList = projectApplyRepository.findAllByMemberid(id);
        return idList;
    }

    public ProjectApply createAppliance(ProjectApply newProject) {
        if (newProject == null || newProject.getMemberid() == null || newProject.getProjectid() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        Optional<ProjectApply> existingProject = projectApplyRepository.findByMemberidAndProjectid(newProject.getMemberid(), newProject.getProjectid());
        if (existingProject.isPresent()) {
            throw new RuntimeException("ProjectApply with given projectid and memberid already exists");
        }
        return projectApplyRepository.save(newProject);
    }

    public boolean withdrawal(ProjectApply newProject) {
        if (newProject == null || newProject.getMemberid() == null || newProject.getProjectid() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        Optional<ProjectApply> existingProject = projectApplyRepository.findByMemberidAndProjectid(newProject.getMemberid(), newProject.getProjectid());
        if (existingProject.isPresent()) {
            projectApplyRepository.delete(existingProject.get());
            return true;
        }
        return false;
    }

    public List<ProjectApply> findAllApplicant(String id) {
        List<ProjectApply> idList = projectApplyRepository.findAllByProjectid(id);
        return idList;
    }

    @Transactional
    public void deleteApplyApproval(List<ProjectApplyDTO> delApplies) {
        try {
//            delApplies.forEach(dto -> {
//                System.out.println("member: " + dto.getMemberid());
//                System.out.println("project: " + dto.getProjectid());
//                System.out.println();
////                projectApplyRepository.deleteByMemberidAndProjectid(dto.getMemberid(), dto.getProjectid());
//            });

            delApplies.forEach(dto -> {
                try {
                    System.out.println("member: " + dto.getMemberid());
                    System.out.println("project: " + dto.getProjectid());
                    System.out.println();
                    projectApplyRepository.deleteByMemberidAndProjectid(dto.getMemberid(), dto.getProjectid());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

//            for (ProjectApplyDTO dto : delApplies) {
//                System.out.println("member: " + dto.getMemberid());
//                System.out.println("project: " + dto.getProjectid());
//                System.out.println();
//                projectApplyRepository.deleteByMemberidAndProjectid(dto.getMemberid(), dto.getProjectid());
//            }


        } catch (Exception e) {
            throw new RuntimeException("Delete Failed/////....");
        }
    }

//    public boolean deleteApplyApproval(List<ProjectJoinDTO> delApplies) {
//
//        delApplies.forEach(delApply -> {
//            if (delApply == null || delApply.getMemberid() == null || delApply.getProjectid() == null) {
//                throw new RuntimeException("Invalid arguments");
//            }
//            Optional<ProjectApply> existingProject = projectApplyRepository.findByMemberidAndProjectid(delApply.getMemberid(), delApply.getProjectid());
//            if (existingProject.isPresent()) {
//                projectApplyRepository.delete(existingProject.get());
//
//            } else {
//                throw new RuntimeException("No USER or PROJECT APPLIANCE");
//            }
//        });
//        return true;
//    }
}
