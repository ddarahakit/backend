package com.ddarahakit.backend.question.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PutQuestionReq {
    private String title;
    private String contents;
}
