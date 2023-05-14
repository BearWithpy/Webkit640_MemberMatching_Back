package com.webkit.project.matchingserver.dto;

import com.webkit.project.matchingserver.domain.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String id;

    private String token;
    private String name;
    private String email;
    private String password;
    private String position;
    private String tech;
    private Integer score;


    public MemberDTO(final MemberEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.position = entity.getPosition();
        this.tech = entity.getTech();
        this.score = entity.getScore();
    }


    public MemberEntity dtoToEntity() {
        return MemberEntity.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .position(position)
                .tech(tech)
                .score(score)
                .build();
    }
}
