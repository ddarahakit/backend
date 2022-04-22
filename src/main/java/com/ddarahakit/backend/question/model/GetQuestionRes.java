package com.ddarahakit.backend.question.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetQuestionRes {
    private Integer idx;
    private String title;
    private String contents;
    private String user_email;
    private Timestamp create_timestamp;
    private Timestamp update_timestamp;
}
