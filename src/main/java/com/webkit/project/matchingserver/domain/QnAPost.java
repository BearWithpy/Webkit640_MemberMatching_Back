package com.webkit.project.matchingserver.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "QNA_POST")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QnAPost {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String writer;
    private String title;
    private String content;

    @CreationTimestamp
    private LocalDateTime createdate;

    @UpdateTimestamp
    private LocalDateTime updatedate;
}
