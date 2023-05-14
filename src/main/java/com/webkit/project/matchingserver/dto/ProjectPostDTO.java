package com.webkit.project.matchingserver.dto;


import com.webkit.project.matchingserver.domain.MemberEntity;
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
public class ProjectPostDTO {

    private String id;
    private String owner;
    private String title;
    private String content;
    private String position;
    private String tech;
    private Integer headcount;
    private LocalDate startdate;



    public ProjectPostDTO(final ProjectPost entity) {
        this.id = entity.getId();
        this.owner = entity.getOwner();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.position = entity.getPosition();
        this.tech = entity.getTech();
        this.headcount = entity.getHeadcount();
        this.startdate =  entity.getStartdate();
    }


    public ProjectPost dtoToEntity() {
        return ProjectPost.builder()
                .id(id)
                .owner(owner)
                .title(title)
                .content(content)
                .position(position)
                .tech(tech)
                .headcount(headcount)
                .startdate(startdate)
                .build();
    }
}
