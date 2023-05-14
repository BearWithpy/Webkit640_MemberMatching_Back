package com.webkit.project.matchingserver.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PROJECT_JOIN")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectJoin {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String projectid;
    private String memberid;
    private String membername;
    private String projectname;
    private LocalDate joindate;
}