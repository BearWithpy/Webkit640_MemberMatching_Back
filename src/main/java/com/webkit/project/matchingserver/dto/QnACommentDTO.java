package com.webkit.project.matchingserver.dto;


import com.webkit.project.matchingserver.domain.QnAComment;
import com.webkit.project.matchingserver.domain.QnAPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnACommentDTO {
    private String id;

    private String writer;
    private String content;

    private String qnaid;

    private LocalDateTime createdate;


    private LocalDateTime updatedate;

    public QnACommentDTO(final QnAComment entity) {
        this.id = entity.getId();
        this.writer = entity.getWriter();
        this.content = entity.getContent();
        this.createdate = entity.getCreatedate();
        this.qnaid = entity.getQnaid();
        this.updatedate = entity.getUpdatedate();
    }


    public QnAComment dtoToEntity() {
        return QnAComment.builder()
                .id(id)
                .writer(writer)
                .content(content)
                .createdate(createdate)
                .qnaid(qnaid)
                .updatedate(updatedate)
                .build();
    }
}
