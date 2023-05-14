package com.webkit.project.matchingserver.dto;

import com.webkit.project.matchingserver.domain.ProjectApply;
import com.webkit.project.matchingserver.domain.ProjectPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectApplyDTO {
    private String id;
    private String projectid;
    private String memberid;

    private String title;
    private LocalDate applydate;

    private String membername;

    public ProjectApplyDTO(final ProjectApply entity) {
        this.id = entity.getId();
        this.projectid = entity.getProjectid();
        this.memberid = entity.getMemberid();
        this.applydate = entity.getApplydate();
        this.title = entity.getTitle();
        this.membername=entity.getMembername();

    }


    public ProjectApply dtoToEntity() {
        return ProjectApply.builder()
                .id(id)
                .title(title)
                .projectid(projectid)
                .memberid(memberid)
                .applydate(applydate)
                .membername(membername)
                .build();
    }
}
