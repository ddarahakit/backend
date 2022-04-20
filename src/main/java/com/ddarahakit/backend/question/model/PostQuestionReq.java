package com.ddarahakit.backend.question.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PostQuestionReq {
    private String title;
    private String contents;
    private Integer chapter_idx;
}
