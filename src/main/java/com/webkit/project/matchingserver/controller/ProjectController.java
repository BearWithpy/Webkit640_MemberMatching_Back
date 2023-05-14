package com.webkit.project.matchingserver.controller;


import com.webkit.project.matchingserver.domain.MemberEntity;
import com.webkit.project.matchingserver.domain.ProjectApply;
import com.webkit.project.matchingserver.domain.ProjectJoin;
import com.webkit.project.matchingserver.domain.ProjectPost;
import com.webkit.project.matchingserver.dto.*;
import com.webkit.project.matchingserver.service.ProjectApplyService;
import com.webkit.project.matchingserver.service.ProjectJoinService;
import com.webkit.project.matchingserver.service.ProjectPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectPostService projectPostService;

    @Autowired
    ProjectApplyService projectApplyService;

    @Autowired
    ProjectJoinService projectJoinService;

    @PostMapping
    public ResponseEntity<?> createProjectPost(@RequestBody ProjectPostDTO projectPostDTO) {
        try {
            log.info(">>>>>>>>>>>>>>>>>>>> " + projectPostDTO);
            ProjectPost newProject = ProjectPost.builder()
                    .owner(projectPostDTO.getOwner())
                    .headcount(projectPostDTO.getHeadcount())
                    .title(projectPostDTO.getTitle())
                    .tech(projectPostDTO.getTech())
                    .position(projectPostDTO.getPosition())
                    .content(projectPostDTO.getContent())
                    .startdate(LocalDate.parse(projectPostDTO.getStartdate().toString()))
                    .build();

            ProjectPost registeredPost = projectPostService.createProject(newProject);
            ProjectPostDTO responsePostDTO = ProjectPostDTO.builder()
                    .id(registeredPost.getId())
                    .title(registeredPost.getTitle())
                    .build();

            return ResponseEntity.ok().body(responsePostDTO);

        } catch (Exception e) {
            ResponseDTO<ProjectPostDTO> response = ResponseDTO.<ProjectPostDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> selectAllProject() {
        try {
            List<ProjectPost> entities = projectPostService.findAllProject();

            List<ProjectPostDTO> todos = entities.stream()
                    .map(ProjectPostDTO::new).collect(Collectors.toList());
            ResponseDTO<ProjectPostDTO> response = ResponseDTO
                    .<ProjectPostDTO>builder().data(todos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<ProjectPostDTO> res = ResponseDTO.<ProjectPostDTO>builder().error("search all list 에러: " + e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> selectProject(@RequestParam("id") String id) {
        try {
//            log.info(">>>>>>>>>>> " + id);
            Optional<ProjectPost> entities = projectPostService.findOne(id);

            List<ProjectPostDTO> todos = entities.stream()
                    .map(ProjectPostDTO::new).collect(Collectors.toList());
            ResponseDTO<ProjectPostDTO> response = ResponseDTO
                    .<ProjectPostDTO>builder().data(todos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<ProjectPostDTO> res = ResponseDTO.<ProjectPostDTO>builder().error("search all list 에러: " + e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/detail/join")
    public ResponseEntity<?> searchJoin(@RequestParam("id") String id) {
        try {
//            log.info(">>>>>>>>>>> " + id);
            Boolean res = projectJoinService.findProjectBeFinished(id);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            ResponseDTO<ProjectPostDTO> res = ResponseDTO.<ProjectPostDTO>builder().error("search all list 에러: " + e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updateProject(@RequestBody ProjectPostDTO projectPostDTO) {
        try {
            log.info(">>>>>>>>>>> " + projectPostDTO);
            ProjectPost entity = projectPostDTO.dtoToEntity();
            List<ProjectPost> entities = projectPostService.updateProject(entity);

            List<ProjectPostDTO> todos = entities.stream()
                    .map(ProjectPostDTO::new).collect(Collectors.toList());
            ResponseDTO<ProjectPostDTO> response = ResponseDTO
                    .<ProjectPostDTO>builder().data(todos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<ProjectPostDTO> res = ResponseDTO.<ProjectPostDTO>builder().error("search all list 에러: " + e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    ///////////////////
    @PostMapping("/apply")
    public ResponseEntity<?> applyProject(@RequestBody ProjectApplyDTO projectApplyDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>> " + projectApplyDTO);
        try {

            ProjectApply newProject = ProjectApply.builder()
                    .title(projectApplyDTO.getTitle())
                    .projectid(projectApplyDTO.getProjectid())
                    .memberid(projectApplyDTO.getMemberid())
                    .membername(projectApplyDTO.getMembername())
                    .applydate(projectApplyDTO.getApplydate())

                    .build();

            ProjectApply registeredApply = projectApplyService.createAppliance(newProject);
            ProjectApplyDTO responsePostDTO = ProjectApplyDTO.builder()
                    .id(registeredApply.getId())
                    .projectid(registeredApply.getProjectid())
                    .memberid(registeredApply.getProjectid())
                    .applydate(registeredApply.getApplydate())
                    .build();

            return ResponseEntity.ok().body(responsePostDTO);

        } catch (Exception e) {
            ResponseDTO<ProjectApplyDTO> response = ResponseDTO.<ProjectApplyDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawProject(@RequestBody ProjectApplyDTO projectApplyDTO) {
        try {
            log.info(">!>@>#>$>%>^> " + projectApplyDTO);

            ProjectApply delProject = ProjectApply.builder()
                    .projectid(projectApplyDTO.getProjectid())
                    .memberid(projectApplyDTO.getMemberid())
                    .applydate(projectApplyDTO.getApplydate())
                    .build();

            boolean registeredApply = projectApplyService.withdrawal(delProject);
            return ResponseEntity.ok().body(registeredApply);

        } catch (Exception e) {
            ResponseDTO<ProjectApplyDTO> response = ResponseDTO.<ProjectApplyDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/applytable")
    public ResponseEntity<?> findMyProject(@RequestParam("id") String id) {
        System.out.println(id);
        try {
            List<ProjectApply> applyList = projectApplyService.findAllMyProject(id);
            return ResponseEntity.ok().body(applyList);
        } catch (Exception e) {
            ResponseDTO<ProjectApplyDTO> response = ResponseDTO.<ProjectApplyDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/jointable")
    public ResponseEntity<?> findMyJoinProject(@RequestParam("id") String id) {

        try {
            List<ProjectJoin> joinList = projectJoinService.findAllMyJoinProject(id);
            return ResponseEntity.ok().body(joinList);
        } catch (Exception e) {
            ResponseDTO<ProjectApplyDTO> response = ResponseDTO.<ProjectApplyDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }



    @GetMapping(value = "/manage/apply")
    public ResponseEntity<?> getApplicant(@RequestParam("id") String id) {
        try {
            System.out.println(id);
            List<ProjectApply> applyList = projectApplyService.findAllApplicant(id);
            return ResponseEntity.ok().body(applyList);
        } catch (Exception e) {
            ResponseDTO<ProjectApplyDTO> response = ResponseDTO.<ProjectApplyDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<?> createJoin(@RequestBody Map<String, List<ProjectJoinDTO>> request){
        List<ProjectJoinDTO> projectJoinDTOs = request.get("projectJoinDTOs");
        try {
//            projectJoinDTOs.forEach(System.out::println);
           projectJoinService.saveProjectJoins(projectJoinDTOs);
        } catch (Exception e) {
            ResponseDTO<ProjectJoinDTO> response = ResponseDTO.<ProjectJoinDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok().build();
    }

//    @DeleteMapping(value = "/approve/delete")
//    @RequestMapping(method = RequestMethod.DELETE, value = "/approve/delete")
    @PostMapping(value = "/approve/delete")
    public ResponseEntity<?> deleteAllWithApproval(@RequestBody Map<String, List<ProjectApplyDTO>> request){
        List<ProjectApplyDTO> projectJoinDTOs = request.get("projectJoinDTOs");
//        projectJoinDTOs.forEach(System.out::println);
        try {
//            projectJoinDTOs.forEach(System.out::println);
             projectApplyService.deleteApplyApproval(projectJoinDTOs);
//            return ResponseEntity.ok().body(result);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            ResponseDTO<ProjectJoinDTO> response = ResponseDTO.<ProjectJoinDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }


    }

}
