package com.webkit.project.matchingserver.service;


import com.webkit.project.matchingserver.domain.MemberEntity;
import com.webkit.project.matchingserver.domain.ProjectApply;
import com.webkit.project.matchingserver.domain.ProjectPost;
import com.webkit.project.matchingserver.domain.QnAPost;
import com.webkit.project.matchingserver.repository.MemberRepository;
import com.webkit.project.matchingserver.repository.ProjectPostRepository;
import com.webkit.project.matchingserver.repository.QnAPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QnAPostService {
    @Autowired
    QnAPostRepository qnAPostRepository;

    @Autowired
    MemberRepository memberRepository;


    public QnAPost createQnA(final QnAPost newQnA) {
        if (newQnA == null || newQnA.getWriter() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        return qnAPostRepository.save(newQnA);
    }

    public List<QnAPost> findAllQnA() {
        List<QnAPost> idList = qnAPostRepository.findAll();
        idList.forEach(qna -> qna.setWriter(memberRepository.findById(qna.getWriter()).get().getName()));
        return idList;
    }

    public Optional<QnAPost> findOne(final String id) {
       QnAPost idList = qnAPostRepository.findById(id).get();
        idList.setWriter(memberRepository.findById(idList.getWriter()).get().getName());
        return qnAPostRepository.findById(id);
    }

    public boolean deleteQnA(final QnAPost delQnA) {
        if (delQnA == null || delQnA.getId() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        Optional<QnAPost> existingProject = qnAPostRepository.findById(delQnA.getId());
        if (existingProject.isPresent()) {
            qnAPostRepository.delete(existingProject.get());
            return true;
        }
        return false;
    }

    public List<QnAPost> retrieve(final String writer) {
        return qnAPostRepository.findByWriter(writer == null ? "" : writer);
    }


    public List<QnAPost> updateQnA(QnAPost entity) {
        final Optional<QnAPost> original = qnAPostRepository.findById(entity.getId());

        original.ifPresent(qna -> {

            qna.setTitle(entity.getTitle());

            qna.setContent(entity.getContent());


            qnAPostRepository.save(qna);
        });

        return retrieve(entity.getWriter());
    }

    public List<QnAPost> findAllQnAByCategoryAndQuery(String category, String query) {
        if(Objects.equals(category, "title")){
            List<QnAPost> idList = qnAPostRepository.findByTitleContaining(query);
            idList.forEach(qna -> qna.setWriter(memberRepository.findById(qna.getWriter()).get().getName()));
            return idList;
        }else{
            List<MemberEntity> writerList = memberRepository.findByNameContaining(query);
            List<String> writerIdList = writerList.stream().map(MemberEntity::getId).collect(Collectors.toList());
            List<QnAPost> idList = qnAPostRepository.findByWriterIn(writerIdList);
            idList.forEach(qna -> qna.setWriter(memberRepository.findById(qna.getWriter()).get().getName()));
            return idList;
        }
    }
}
