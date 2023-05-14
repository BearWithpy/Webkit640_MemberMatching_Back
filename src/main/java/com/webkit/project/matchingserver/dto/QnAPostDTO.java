package com.webkit.project.matchingserver.dto;


import com.webkit.project.matchingserver.domain.ProjectPost;
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
public class QnAPostDTO {
    private String id;
    private String writer;
    private String title;
    private String content;

    private LocalDateTime createdate;

    private LocalDateTime updatedate;


    public QnAPostDTO(final QnAPost entity) {
        this.id = entity.getId();
        this.writer = entity.getWriter();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdate = entity.getCreatedate();
        this.updatedate = entity.getUpdatedate();
    }


    public  QnAPost dtoToEntity() {
        return QnAPost.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .createdate(createdate)
                .updatedate(updatedate)
                .build();
    }
}
