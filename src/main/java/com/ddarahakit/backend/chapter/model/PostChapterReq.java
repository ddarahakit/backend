package com.ddarahakit.backend.chapter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PostChapterReq {
    private Integer num;
    private String name;
    private Time time;
    private String detail;
    private Integer course_idx;
}