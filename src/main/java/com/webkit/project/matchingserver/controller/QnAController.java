package com.webkit.project.matchingserver.controller;


import com.webkit.project.matchingserver.domain.ProjectPost;
import com.webkit.project.matchingserver.domain.QnAComment;
import com.webkit.project.matchingserver.domain.QnAPost;
import com.webkit.project.matchingserver.dto.ProjectPostDTO;
import com.webkit.project.matchingserver.dto.QnACommentDTO;
import com.webkit.project.matchingserver.dto.QnAPostDTO;
import com.webkit.project.matchingserver.dto.ResponseDTO;
import com.webkit.project.matchingserver.repository.QnAPostRepository;
import com.webkit.project.matchingserver.service.QnACommentService;
import com.webkit.project.matchingserver.service.QnAPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/qna")
public class QnAController {
    @Autowired
    QnAPostService qnAPostService;

    @Autowired
    QnACommentService qnACommentService;

    @GetMapping
    public ResponseEntity<?> selectAllProject(@RequestParam("query") String query, @RequestParam("category") String category) {
//        log.info(query);
//        log.info(category);
        if (Objects.equals(query, "")){
            log.info("Query Absent");
            try {
                List<QnAPost> entities = qnAPostService.findAllQnA();

                List<QnAPostDTO> todos = entities.stream()
                        .map(QnAPostDTO::new).collect(Collectors.toList());
                ResponseDTO<QnAPostDTO> response = ResponseDTO
                        .<QnAPostDTO>builder().data(todos).build();

                return ResponseEntity.ok().body(response);
            } catch (Exception e) {
                ResponseDTO<QnAPostDTO> res = ResponseDTO.<QnAPostDTO>builder().error("search all list 에러: " + e.getMessage()).build();
                return ResponseEntity.badRequest().body(res);
            }
        }else{
            try {
                List<QnAPost> entities = qnAPostService.findAllQnAByCategoryAndQuery(category, query);

                List<QnAPostDTO> todos = entities.stream()
                        .map(QnAPostDTO::new).collect(Collectors.toList());
                ResponseDTO<QnAPostDTO> response = ResponseDTO
                        .<QnAPostDTO>builder().data(todos).build();

                return ResponseEntity.ok().body(response);
            } catch (Exception e) {
                ResponseDTO<QnAPostDTO> res = ResponseDTO.<QnAPostDTO>builder().error("search all list 에러: " + e.getMessage()).build();
                return ResponseEntity.badRequest().body(res);
            }
        }

    }

    @PostMapping
    public ResponseEntity<?> createQnA(@RequestBody QnAPostDTO qnAPostDTO) {
        try {
            log.info(">>>>>>>>>>>>>>>>>>>> " + qnAPostDTO);
            QnAPost newQnA = QnAPost.builder()
                    .writer(qnAPostDTO.getWriter())
                    .title(qnAPostDTO.getTitle())
                    .content(qnAPostDTO.getContent())
                    .build();

            QnAPost registeredPost = qnAPostService.createQnA(newQnA);
            QnAPostDTO responsePostDTO = QnAPostDTO.builder()
                    .id(registeredPost.getId())
                    .title(registeredPost.getTitle())
                    .build();

            return ResponseEntity.ok().body(responsePostDTO);

        } catch (Exception e) {
            ResponseDTO<QnAPostDTO> response = ResponseDTO.<QnAPostDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/delete")
    public ResponseEntity<?> deleteQnA(@RequestBody QnAPostDTO qnAPostDTO) {
        try {
            log.info(">>>>>>>>>>>>>>>>>>>> " + qnAPostDTO);
            QnAPost delQnA = QnAPost.builder()
                    .id(qnAPostDTO.getId())
                    .build();


            boolean registeredQna = qnAPostService.deleteQnA(delQnA);
            return ResponseEntity.ok().body(registeredQna);


        } catch (Exception e) {
            ResponseDTO<QnAPostDTO> response = ResponseDTO.<QnAPostDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping("/detail")
    public ResponseEntity<?> selectQnA(@RequestParam("id") String id) {
        try {

            Optional<QnAPost> entities = qnAPostService.findOne(id);

            List<QnAPostDTO> qnas = entities.stream()
                    .map(QnAPostDTO::new).collect(Collectors.toList());
            ResponseDTO<QnAPostDTO> response = ResponseDTO
                    .<QnAPostDTO>builder().data(qnas).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<QnAPostDTO> res = ResponseDTO.<QnAPostDTO>builder().error("search all list 에러: " + e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }


    @PostMapping("/comment")
    public ResponseEntity<?> createQnA(@RequestBody QnACommentDTO qnACommentDTO) {
        try {
            log.info(">>>>>>>>>>>>>>>>>>>> " + qnACommentDTO);
            QnAComment newComment = QnAComment.builder()
                    .writer(qnACommentDTO.getWriter())
                    .qnaid(qnACommentDTO.getQnaid())
                    .content(qnACommentDTO.getContent())
                    .build();

            QnAComment registeredComment = qnACommentService.createComment(newComment);
            QnACommentDTO responsePostDTO = QnACommentDTO.builder()
                    .id(registeredComment.getId())
                    .qnaid(registeredComment.getQnaid())
                    .build();

            return ResponseEntity.ok().body(responsePostDTO);

        } catch (Exception e) {
            ResponseDTO<QnACommentDTO> response = ResponseDTO.<QnACommentDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<?> selectComment(@RequestParam("id") String id) {
        try {
            List<QnAComment> entities = qnACommentService.findAllCommentOfPost(id);

            List<QnACommentDTO> comments = entities.stream()
                    .map(QnACommentDTO::new).collect(Collectors.toList());
            ResponseDTO<QnACommentDTO> response = ResponseDTO
                    .<QnACommentDTO>builder().data(comments).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<QnACommentDTO> res = ResponseDTO.<QnACommentDTO>builder().error("search all list 에러: " + e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updateProject(@RequestBody QnAPostDTO qnAPostDTO) {
        try {
            log.info(">>>>>>>>>>> " + qnAPostDTO);
            QnAPost entity = qnAPostDTO.dtoToEntity();
            List<QnAPost> entities = qnAPostService.updateQnA(entity);

            List<QnAPostDTO> qnas = entities.stream()
                    .map(QnAPostDTO::new).collect(Collectors.toList());
            ResponseDTO<QnAPostDTO> response = ResponseDTO
                    .<QnAPostDTO>builder().data(qnas).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<QnAPostDTO> res = ResponseDTO.<QnAPostDTO>builder().error("search all list 에러: " + e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }


    @PostMapping ("/comment/del")
    public ResponseEntity<?> deleteComment(@RequestBody QnACommentDTO qnACommentDTO) {
        try {
            QnAComment delQnA = QnAComment.builder()
                    .id(qnACommentDTO.getId())
                    .build();

            System.out.println(delQnA);
            boolean registeredComment = qnACommentService.deleteComment(delQnA);
            return ResponseEntity.ok().body(registeredComment);


        } catch (Exception e) {
            ResponseDTO<QnACommentDTO> res = ResponseDTO.<QnACommentDTO>builder().error("search all list 에러: " + e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }


}
