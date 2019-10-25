package com.study.webfluxtodo.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yonghui
 * @since 2019. 10. 24
 */
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    private Long id;
    @Column
    private String content;
    @Column
    private boolean done;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
}
