package com.webkit.project.matchingserver.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PROJECT_POST")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectPost {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String owner;
    private String title;

    @Column(length = 5000)
    private String content;

    private String position;
    private String tech;
    private Integer headcount;

    private LocalDate startdate;


}
