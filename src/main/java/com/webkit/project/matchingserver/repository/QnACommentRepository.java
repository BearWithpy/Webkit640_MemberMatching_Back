package com.webkit.project.matchingserver.repository;

import com.webkit.project.matchingserver.domain.QnAComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QnACommentRepository extends JpaRepository<QnAComment, String> {

    List<QnAComment> findAllByQnaid(final String qnaid);
}
