package com.webkit.project.matchingserver.service;


import com.webkit.project.matchingserver.domain.QnAComment;
import com.webkit.project.matchingserver.domain.QnAPost;
import com.webkit.project.matchingserver.repository.MemberRepository;
import com.webkit.project.matchingserver.repository.QnACommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class QnACommentService {

    @Autowired
    QnACommentRepository qnACommentRepository;

    @Autowired
    MemberRepository memberRepository;

    public QnAComment createComment(final QnAComment newComment) {
        if (newComment == null || newComment.getWriter() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        return qnACommentRepository.save(newComment);
    }

    public List<QnAComment> findAllCommentOfPost(final String qnaPostId) {
        List<QnAComment> idList = qnACommentRepository.findAllByQnaid(qnaPostId);
        idList.forEach(qna -> qna.setWriter(memberRepository.findById(qna.getWriter()).get().getName()));
        return idList;
    }

    public boolean deleteComment(final QnAComment delComment) {
        if (delComment == null || delComment.getId() == null) {
            throw new RuntimeException("Invalid arguments");
        }


        System.out.println(delComment);

        Optional<QnAComment> existingProject = qnACommentRepository.findById(delComment.getId());
        if (existingProject.isPresent()) {
            qnACommentRepository.delete(existingProject.get());
            return true;
        }
        return false;
    }

}
