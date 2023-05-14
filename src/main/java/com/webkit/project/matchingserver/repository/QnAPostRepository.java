package com.webkit.project.matchingserver.repository;

import com.webkit.project.matchingserver.domain.MemberEntity;
import com.webkit.project.matchingserver.domain.QnAPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QnAPostRepository extends JpaRepository<QnAPost, String> {

    List<QnAPost> findByWriter(final String writer);

    List<QnAPost> findByWriterContaining(String writer);

    List<QnAPost> findByTitleContaining(final String title);

    List<QnAPost> findByWriterIn(List<String> writerIdList);
}
