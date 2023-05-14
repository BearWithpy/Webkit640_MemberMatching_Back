package com.webkit.project.matchingserver.dto;


import com.webkit.project.matchingserver.domain.ProjectApply;
import com.webkit.project.matchingserver.domain.ProjectJoin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectJoinDTO {
    private String id;
    private String projectid;
    private String memberid;

    private String membername;

    private String projectname;
    private LocalDate joindate;

    public ProjectJoinDTO(final ProjectJoin entity) {
        this.id = entity.getId();
        this.projectid = entity.getProjectid();
        this.memberid = entity.getMemberid();
        this.joindate = entity.getJoindate();
        this.membername = entity.getMembername();
        this.projectname = entity.getProjectname();

    }


    public ProjectJoin dtoToEntity() {
        return ProjectJoin.builder()
                .id(id)
                .memberid(memberid)
                .projectid(projectid)
                .membername(membername)
                .joindate(joindate)
                .projectname(projectname)
                .build();
    }
}
